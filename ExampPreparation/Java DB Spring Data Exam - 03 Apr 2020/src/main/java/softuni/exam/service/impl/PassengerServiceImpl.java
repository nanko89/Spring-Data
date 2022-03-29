package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.PassengerSeedDto;
import softuni.exam.models.entity.Passenger;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {

    public static final String PASSENGER_FILE_PATH = "src/main/resources/files/json/passengers.json";

    private final PassengerRepository passengerRepository;
    private final TownService townService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, TownService townService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.passengerRepository = passengerRepository;
        this.townService = townService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(PASSENGER_FILE_PATH));
    }

    @Override
    public String importPassengers() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readPassengersFileContent(), PassengerSeedDto[].class))
                .filter(passengerSeedDto -> {
                    boolean isValid = validationUtil.isValid(passengerSeedDto)
                            && !isExistEntity(passengerSeedDto.getEmail())
                            && townService.isExistEntity(passengerSeedDto.getTown());

                    sb.append(isValid
                                    ? String.format("Successfully imported Passenger %s - %s",
                                    passengerSeedDto.getLastName(), passengerSeedDto.getEmail())
                                    : "Invalid Passenger")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(passengerSeedDto -> {
                    Passenger passenger = modelMapper.map(passengerSeedDto, Passenger.class);
                    passenger.setTown(townService.findByName(passengerSeedDto.getTown()));
                    return passenger;
                })
                .forEach(passengerRepository::save);

        return sb.toString();
    }

    @Override
    @Transactional
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        return passengerRepository.findAllByOrderByTicketsDescEmailAsc()
                .stream()
                .map(p -> String.format("Passenger %s %s\n" +
                        "\tEmail - %s\n" +
                        "\tPhone - %s\n" +
                        "\tNumber of tickets - %d\n",
                        p.getFirstName(), p.getLastName(), p.getEmail(), p.getPhoneNumber(), p.getTickets().size()))
                .collect(Collectors.joining());
    }

    @Override
    public boolean isExistEntity(String email) {
        return passengerRepository.existsByEmail(email);

    }

    @Override
    public Passenger findByEmail(String email) {
        return passengerRepository.findByEmail(email);
    }
}

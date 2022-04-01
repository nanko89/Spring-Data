package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.TicketSeedRootDto;
import softuni.exam.models.entity.Ticket;
import softuni.exam.repository.TicketRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.service.PlaneService;
import softuni.exam.service.TicketService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TicketServiceImpl implements TicketService {

    public static final String TICKET_FILE_PATH = "src/main/resources/files/xml/tickets.xml";

    private final TicketRepository ticketRepository;
    private final TownService townService;
    private final PlaneService planeService;
    private final PassengerService passengerService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, TownService townService, PlaneService planeService, PassengerService passengerService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.ticketRepository = ticketRepository;
        this.townService = townService;
        this.planeService = planeService;
        this.passengerService = passengerService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(TICKET_FILE_PATH));
    }

    @Override
    public String importTickets() throws JAXBException, FileNotFoundException {

        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(TICKET_FILE_PATH, TicketSeedRootDto.class)
                .getTickets()
                .stream()
                .filter(ticketSeedDto -> {

                    boolean isValid = validationUtil.isValid(ticketSeedDto)
                            && !isExistEntity(ticketSeedDto.getSerialNumber())
                            && townService.isExistEntity(ticketSeedDto.getFromTown().getName())
                            && townService.isExistEntity(ticketSeedDto.getToTown().getName())
                            && passengerService.isExistEntity(ticketSeedDto.getPassenger().getEmail())
                            && planeService.isExistEntity(ticketSeedDto.getPlane().getRegisterNumber());

                    sb.append(isValid
                                    ? String.format("Successfully imported Ticket %s - %s",
                                    ticketSeedDto.getFromTown().getName(), ticketSeedDto.getToTown().getName())
                                    : "Invalid Ticket")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(ticketSeedDto -> {
                    Ticket ticket = modelMapper.map(ticketSeedDto, Ticket.class);
                    ticket.setFromTown(townService.findByName(ticketSeedDto.getFromTown().getName()));
                    ticket.setToTown(townService.findByName(ticketSeedDto.getToTown().getName()));
                    ticket.setPassenger(passengerService.findByEmail(ticketSeedDto.getPassenger().getEmail()));
                    ticket.setPlane(planeService.findByRegisterNumber(ticketSeedDto.getPlane().getRegisterNumber()));
                    ticket.setTakeOff(LocalDateTime.parse(
                            ticketSeedDto.getTakeOff(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    return ticket;
                })
                .forEach(ticketRepository::save);

        return sb.toString();
    }

    private boolean isExistEntity(String serialNumber) {
        return ticketRepository.existsBySerialNumber(serialNumber);
    }
}

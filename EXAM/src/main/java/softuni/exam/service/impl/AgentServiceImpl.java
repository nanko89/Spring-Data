package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.AgentSeedDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.repository.AgentRepository;
import softuni.exam.service.AgentService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class AgentServiceImpl implements AgentService {

    public static final String AGENT_PATH = "src/main/resources/files/json/agents.json";

    private final AgentRepository agentRepository;
    private final TownService townService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository, TownService townService, ModelMapper modelMapper,
                            ValidationUtil validationUtil, Gson gson) {
        this.agentRepository = agentRepository;
        this.townService = townService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(AGENT_PATH));
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readAgentsFromFile(), AgentSeedDto[].class))
                .filter(agentSeedDto -> {
                    boolean isValid = validationUtil.isValid(agentSeedDto)
                            && !isExistAgent(agentSeedDto.getFirstName(), agentSeedDto.getEmail())
                            && townService.isExistTown(agentSeedDto.getTown());

                    sb.append(isValid
                    ? String.format("Successfully imported agent - %s %s",
                            agentSeedDto.getFirstName(), agentSeedDto.getLastName())
                            : "Invalid agent")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(agentSeedDto -> {
                    Agent agent = modelMapper.map(agentSeedDto, Agent.class);
                    agent.setTown(townService.findByTownName(agentSeedDto.getTown()));
                    return agent;
                })
                .forEach(agentRepository::save);

        return sb.toString().trim();
    }

    private boolean isExistAgent(String firstName, String email) {
        return agentRepository.existsByFirstNameOrAndEmail(firstName,email);
    }

    @Override
    public boolean isExistByName(String name){
        return agentRepository.existsByFirstName(name);
    }

    @Override
    public Agent findByName(String name) {
        return agentRepository.findByFirstName(name);
    }
}

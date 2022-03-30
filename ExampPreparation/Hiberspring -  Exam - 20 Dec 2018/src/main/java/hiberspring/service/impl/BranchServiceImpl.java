package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.json.BranchSeedDto;
import hiberspring.domain.entities.Branch;
import hiberspring.repository.BranchRepository;
import hiberspring.service.BranchService;
import hiberspring.service.TownService;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class BranchServiceImpl implements BranchService {

    public static final String BRANCH_FILE_PATH = "src/main/resources/files/branches.json";

    private final BranchRepository branchRepository;
    private final TownService townService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final FileUtil fileUtil;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, TownService townService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, FileUtil fileUtil) {
        this.branchRepository = branchRepository;
        this.townService = townService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.fileUtil = fileUtil;
    }

    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return fileUtil.readFile(BRANCH_FILE_PATH);
    }

    @Override
    public String importBranches(String branchesFileContent) throws IOException {

        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readBranchesJsonFile(), BranchSeedDto[].class))
                .filter(branchSeedDto -> {
                    boolean isValid = validationUtil.isValid(branchSeedDto)
                            && townService.isExistEntity(branchSeedDto.getTown());

                    sb.append(isValid
                    ? String.format("Successfully imported Branch %s.", branchSeedDto.getName())
                            : "Error: Invalid data.")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(branchSeedDto -> {
                    Branch branch = modelMapper.map(branchSeedDto, Branch.class);
                    branch.setTown(townService.findByName(branchSeedDto.getTown()));
                    return branch;
                })
                .forEach(branchRepository::save);

        return sb.toString();
    }

    @Override
    public boolean isExistEntity(String name) {
        return branchRepository.existsByName(name);
    }

    @Override
    public Branch findByName(String name) {
        return branchRepository.findByName(name);
    }
}

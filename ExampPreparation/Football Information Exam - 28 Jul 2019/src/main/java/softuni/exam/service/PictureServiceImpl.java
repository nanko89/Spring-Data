package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.xml.PictureSeedRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParse;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {

    private static final String PICTURE_FILE_PATH = "src/main/resources/files/xml/pictures.xml";

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;
    private final XmlParse xmlParse;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper,
                              ValidatorUtil validatorUtil, FileUtil fileUtil, XmlParse xmlParse) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
        this.xmlParse = xmlParse;
    }

    @Override
    public String importPictures() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        xmlParse.fromFile(PICTURE_FILE_PATH, PictureSeedRootDto.class)
                .getPictures()
                .stream()
                .filter(pictureSeedDto -> {
                    boolean isValid = validatorUtil.isValid(pictureSeedDto);

                    sb.append(isValid
                                    ? String.format("Successfully imported picture - %s", pictureSeedDto.getUrl())
                                    : "Invalid Picture")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(pictureSeedDto -> modelMapper.map(pictureSeedDto, Picture.class))
                .forEach(pictureRepository::save);

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return fileUtil.readFile(PICTURE_FILE_PATH);
    }

    @Override
    public boolean isExistEntity(String url) {
        return pictureRepository.existsByUrl(url);
    }

    @Override
    public Picture findByUrl(String url) {
        return pictureRepository.findByUrl(url);
    }
}

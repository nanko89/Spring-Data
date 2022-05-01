package softuni.exam.service;

import softuni.exam.domain.entities.Picture;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface PictureService {
    String importPictures() throws JAXBException, FileNotFoundException;

    boolean areImported();

    String readPicturesXmlFile() throws IOException;

    boolean isExistEntity(String url);

    Picture findByUrl(String url);
}

package gr.thegoodsideofe1.tourguide.services;

import gr.thegoodsideofe1.tourguide.entities.Image;
import gr.thegoodsideofe1.tourguide.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    public List<Image> listAllImages(){
        return imageRepository.findAll();
    }

    public Image getImage(Integer id){
        return imageRepository.findById(id).get();
    }


    public Page<Image> getImageByTitle(String title, Pageable pageable){
        return  imageRepository.findAllImagesByTitle(title, pageable);
    }
}

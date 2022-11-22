package gr.thegoodsideofe1.tourguide.repositories;

import gr.thegoodsideofe1.tourguide.entities.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ImageRepository extends CrudRepository<Image, Integer> {
    public List<Image> findAll();

    @Query(value = "SELECT i FROM Image i WHERE i.title LIKE %:title% OR i.description LIKE %:title%")
    Page<Image> findAllImagesByTitle(@Param("title") String title, Pageable pageable);

}

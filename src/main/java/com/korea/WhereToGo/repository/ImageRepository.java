package com.korea.WhereToGo.repository;

import com.korea.WhereToGo.entity.ImageEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, String> {
    List<ImageEntity> findByContentId(String contentId);

    @Transactional
    void deleteByContentId(String contentId);
}
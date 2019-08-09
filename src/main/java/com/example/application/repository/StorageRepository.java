package com.example.application.repository;

import com.example.application.model.Album;
import com.example.application.model.Storage;
import com.example.application.utils.StorageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Integer> {
    @Query("select u from Storage u where u.type = :type ")
    Storage findByType (@Param("type")StorageType type);
}

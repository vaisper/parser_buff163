package com.parser.parser.data.repo;

import com.parser.parser.data.entity.Stickers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StickersRepository extends JpaRepository<Stickers, Integer>, CrudRepository<Stickers, Integer> {
    Stickers findByShortName(String shortName);
}

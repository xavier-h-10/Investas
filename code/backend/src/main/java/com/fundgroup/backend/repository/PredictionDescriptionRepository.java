package com.fundgroup.backend.repository;

import com.fundgroup.backend.entity.PredictionDescription;
import java.util.List;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

public interface PredictionDescriptionRepository extends JpaRepository<PredictionDescription, Integer> {

  @Override
  @NonNull
  @Query(value = "from PredictionDescription order by priority desc")
  List<PredictionDescription> findAll();
}

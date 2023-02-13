package org.task.hr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.task.hr.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

}

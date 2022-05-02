package com.qbros.testcomplete.persistence;

import com.qbros.testcomplete.service.models.GENDER;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@Entity
@ToString
@Table(name = "Persons")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private long personId;
    private String name;
    private int age;
    @Enumerated(EnumType.STRING)
    private GENDER gender;

    protected PersonEntity() {
        //jpa
    }

    public PersonEntity(long personId, String name, int age, GENDER gender) {
        this.personId = personId;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    @Builder(builderMethodName = "aPersonEntity", setterPrefix = "with", toBuilder = true)
    private static PersonEntity buildPersonEntity(long personId, String name, int age, GENDER gender) {
        return new PersonEntity(personId, name, age, gender);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEntity that = (PersonEntity) o;
        return personId == that.personId && age == that.age && name.equals(that.name) && gender == that.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, name, age, gender);
    }
}

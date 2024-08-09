package com.projects.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DS_SUPERVISORS")
@Getter
@Setter
public class Supervisor {
    String name;
    @Id
    String registration_no ;
    String specialization ;
    String department ;
}

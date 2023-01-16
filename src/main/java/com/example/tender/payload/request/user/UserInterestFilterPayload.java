package com.example.tender.payload.request.user;

import lombok.Data;

/**
 * @author 'Mukhtarov Sarvarbek' on 14.01.2023
 * @project tender
 * @contact @sarvargo
 */
@Data
public class UserInterestFilterPayload {

    private Integer toAge;
    private Integer fromAge;

    private Double distance;

    private Boolean gender; // true - Male, false - Female, null - all

}

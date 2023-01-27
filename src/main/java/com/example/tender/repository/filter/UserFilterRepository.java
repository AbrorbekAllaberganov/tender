package com.example.tender.repository.filter;

import com.example.tender.entity.enums.UserStatus;
import com.example.tender.entity.users.User;
import com.example.tender.mapper.user.UserMapper;
import com.example.tender.payload.request.user.UserInterestFilterPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author 'Mukhtarov Sarvarbek' on 14.01.2023
 * @project tender
 * @contact @sarvargo
 */
@Repository
@RequiredArgsConstructor
public class UserFilterRepository {

    private final EntityManager entityManager;

    public UserMapper filter(UserInterestFilterPayload payload,
                             int page, int size, double lat, double lon,
                             List<String> interests, List<String> languages) {
        StringBuilder sqlQuery = new StringBuilder();

        StringBuilder countQuery =
                new StringBuilder("select count(*) from users as u " +
                        " inner join users_interests ui on u.id = ui.users_id " +
                        " where  ui.interests in (:interests) and ul.languages in (:languages) ");

        String sql = "select distinct u.* from users as u" +
                " inner join users_interests ui on u.id = ui.users_id " +
                " inner join users_languages ul on u.id = ul.users_id" +
                " where ui.interests in (:interests) and ul.languages in (:languages) ";

        Map<String, Object> param = new HashMap<>();

        param.put("interests", interests);
        param.put("languages", languages);
//        param.put("status", UserStatus.ONLINE.name());

        sqlQuery.append(sql);
        if (Optional.ofNullable(payload).isPresent()) {

            if (Optional.ofNullable(payload.getToAge()).isPresent()) {
                sqlQuery.append(" and extract('year' from age(u.birth_day)) <= :toAge ");
                countQuery.append(" and extract('year' from age(u.birth_day)) <= :toAge ");
                param.put("toAge", payload.getToAge());
            }
            if (Optional.ofNullable(payload.getFromAge()).isPresent()) {
                sqlQuery.append(" and extract('year' from age(u.birth_day)) >= :fromAge ");
                countQuery.append(" and extract('year' from age(u.birth_day)) >= :fromAge ");
                param.put("fromAge", payload.getFromAge());
            }

            if (Optional.ofNullable(payload.getGender()).isPresent()) {
                sqlQuery.append(" and user.gender >= :gender ");
                countQuery.append(" and user.gender >= :gender ");
                param.put("gender", payload.getGender());
            }

            if (Optional.ofNullable(payload.getDistance()).isPresent()) {
                sqlQuery.append(" and (6371 * acos(cos(radians(:lat)) * cos(radians(u.lat)) * cos(radians(u.lon) - radians(:lon)) + sin(radians(:lat)) * sin(radians(u.lat)))) <= :distance ");
                countQuery.append("and (6371 * acos(cos(radians(:lat)) * cos(radians(u.lat)) * cos(radians(u.lon) - radians(:lon)) + sin(radians(:lat)) * sin(radians(u.lat)))) <= :distance ");
                param.put("distance", 5);
                param.put("lat", lat);
                param.put("lon", lon);
            }
        }

        Query query = entityManager.createNativeQuery(sql, User.class);
//        Query query = entityManager.createNativeQuery(sqlQuery.toString(), User.class);
        Query count = entityManager.createNativeQuery(countQuery.toString());
        param.forEach(query::setParameter);
        param.forEach(count::setParameter);
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        long countAll = ((Number) count.getSingleResult()).longValue();

        List<User> resList = query.getResultList();

        return new UserMapper(resList, countAll);
    }

}

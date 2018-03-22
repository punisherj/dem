package com.bonc.dem.repository;

import com.bonc.dem.entity.ExcelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

public interface ExcelRepository extends JpaRepository<ExcelEntity, BigInteger> {

    @Query(value="select * from oc_excel where date = ?1",nativeQuery = true)
    List<ExcelEntity> findByDate(String date);

    @Query(value="select sum(amount) from oc_excel where city = ?1 and date like ?2",nativeQuery = true)
    Integer findAmountByCityAndMonth(String city, String date);

    @Query(value="select sum(success) from oc_excel where city = ?1 and date like ?2",nativeQuery = true)
    Integer findSuccessByCityAndMonth(String city, String date);

    @Query(value="select date from oc_excel where date like ?1 GROUP BY date asc",nativeQuery = true)
    List<String> findAllDate(String date);

    @Query(value = "delete from oc_excel where date = ?1 ", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteByDate(String date);
}

package com.example.demo.repository;
import com.example.demo.model.Status;
import com.example.demo.model.WorkOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    @Query("SELECT w FROM WorkOrder w WHERE " +
           "(:status IS NULL OR w.status = :status) AND " +
           "(:technicianId IS NULL OR w.technician.id = :technicianId)")
    Page<WorkOrder> findByFilters(@Param("status") Status status, 
                                  @Param("technicianId") Long technicianId, 
                                  Pageable pageable);
    long countByStatusIn(java.util.List<com.example.demo.model.Status> statuses);
}
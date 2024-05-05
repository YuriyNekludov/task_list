package yuriy.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "task_schema", name = "t_tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "c_title", nullable = false)
    private String title;

    @Column(name = "c_description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "c_status", nullable = false)
    private TaskStatus status;

    @Column(name = "c_completed_at")
    private LocalDateTime completedAt;
}

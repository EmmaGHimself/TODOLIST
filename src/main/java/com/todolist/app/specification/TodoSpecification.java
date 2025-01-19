package com.todolist.app.specification;

import com.todolist.app.model.TodoItem;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Specification class to filter TodoItem entities.
 */
public class TodoSpecification {

    /**
     * Filters TodoItem entities by priority and completion status.
     * @param priority the priority level to filter by
     * @param completed the completion status to filter by
     * @return Specification for filtering TodoItems
     */
    public static Specification<TodoItem> filterByPriorityAndCompleted(Integer priority, Boolean completed) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (priority != null) {
                predicates.add(criteriaBuilder.equal(root.get("priority"), priority));
            }
            if (completed != null) {
                predicates.add(criteriaBuilder.equal(root.get("completed"), completed));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

package com.deep_search.deep_search.dto;

import com.deep_search.deep_search.entity.CustomerQuery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserQueriesResponse {

    private boolean success;
    private String message;
    private List<QueryItem> queries;

    public UserQueriesResponse() {
        this.queries = new ArrayList<>();
    }

    public static UserQueriesResponse success(String message, List<CustomerQuery> queries) {
        UserQueriesResponse response = new UserQueriesResponse();
        response.success = true;
        response.message = message;
        response.queries = queries.stream()
                .map(QueryItem::from)
                .toList();
        return response;
    }

    public static UserQueriesResponse error(String message) {
        UserQueriesResponse response = new UserQueriesResponse();
        response.success = false;
        response.message = message;
        return response;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<QueryItem> getQueries() {
        return queries;
    }

    public void setQueries(List<QueryItem> queries) {
        this.queries = queries;
    }

    // Inner class for query items
    public static class QueryItem {
        private Long queryId;
        private String subject;
        private String message;
        private LocalDateTime createdAt;
        private String status;

        public QueryItem() {
        }

        public static QueryItem from(CustomerQuery query) {
            QueryItem item = new QueryItem();
            item.queryId = query.getId();
            item.subject = query.getSubject();
            item.message = query.getMessage();
            item.createdAt = query.getCreatedAt();
            item.status = query.getStatus();
            return item;
        }

        // Getters and Setters
        public Long getQueryId() {
            return queryId;
        }

        public void setQueryId(Long queryId) {
            this.queryId = queryId;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}


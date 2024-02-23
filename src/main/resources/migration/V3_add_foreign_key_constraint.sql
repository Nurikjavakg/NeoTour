ALTER TABLE trips
    ADD CONSTRAINT fk_featured_user
        FOREIGN KEY (featured_user_id) REFERENCES user(user_id);
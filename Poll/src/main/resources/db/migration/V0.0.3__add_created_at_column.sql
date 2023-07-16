ALTER TABLE poll ADD COLUMN created_at timestamp DEFAULT current_timestamp;
ALTER TABLE option ADD COLUMN created_at timestamp DEFAULT current_timestamp;

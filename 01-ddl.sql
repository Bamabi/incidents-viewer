-- Drop tables if they exist
DROP TABLE IF EXISTS incident CASCADE;
DROP TABLE IF EXISTS person CASCADE;

CREATE TABLE person
(
    id         SERIAL PRIMARY KEY,
    last_name  TEXT NOT NULL,
    first_name TEXT NOT NULL,
    email      TEXT NULL
);

CREATE TABLE incident
(
    id          SERIAL PRIMARY KEY,
    title       TEXT      NOT NULL,
    description TEXT      NOT NULL,
    severity    TEXT      NOT NULL,
    owner_id    INTEGER   NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (owner_id) REFERENCES person (id)
);

CREATE INDEX idx_person_id   ON person (id);
CREATE INDEX idx_incident_id ON incident (id);

-- Add GIN indexes for full-text search optimization
CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE INDEX idx_incident_title_gin       ON incident USING gin (title gin_trgm_ops);
CREATE INDEX idx_incident_description_gin ON incident USING gin (description gin_trgm_ops);
CREATE INDEX idx_incident_severity_gin    ON incident USING gin (severity gin_trgm_ops);

CREATE INDEX idx_person_last_name_gin  ON person USING gin (last_name gin_trgm_ops);
CREATE INDEX idx_person_first_name_gin ON person USING gin (first_name gin_trgm_ops);
CREATE INDEX idx_person_email_gin      ON person USING gin (email gin_trgm_ops);

-- Add composite indexes for common query patterns
CREATE INDEX idx_incident_severity_created ON incident (severity, created_at DESC);
CREATE INDEX idx_incident_owner_created    ON incident (owner_id, created_at DESC);
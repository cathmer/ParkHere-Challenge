CREATE TABLE reservations (
    id SERIAL PRIMARY KEY,
    start_time BIGINT,
    end_time BIGINT,
    user_id VARCHAR(100),
    spot_id INTEGER,
    parking_lot_id INTEGER
);

CREATE TABLE parking_spots (
    spot_id INTEGER,
    spot_name VARCHAR(100),
    parking_lot_id INTEGER,
    priority INTEGER,
    PRIMARY KEY (spot_id, parking_lot_id)
);

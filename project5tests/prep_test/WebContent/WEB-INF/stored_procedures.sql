DELIMITER $$
CREATE PROCEDURE add_movie(
    IN m_title varchar(100),
    IN m_year int, 
    IN m_director varchar(100), 
    IN m_banner_url varchar(100), 
    IN m_trailer_url varchar(100), 
    IN m_firstname varchar(50),
    IN m_lastname varchar(50),
    IN m_genre varchar(50))
BEGIN
	
    DECLARE success INT DEFAULT 1;
    
	DECLARE movie_exists INT DEFAULT 0;
    DECLARE star_exists INT DEFAULT 0;
    DECLARE genre_exists INT DEFAULT 0;
    
    DECLARE temp_movie_id INT DEFAULT 0;
    DECLARE temp_genre_id INT DEFAULT 0;
    DECLARE temp_star_id INT DEFAULT 0;
	-- Checks if movie, star or genre is in the database
    SET movie_exists = (SELECT COUNT(*) FROM movies WHERE title = m_title);
    SET star_exists = (SELECT COUNT(*) FROM stars WHERE first_name=m_firstname and last_name=m_lastname);
    SET genre_exists = (SELECT COUNT(*) FROM genres WHERE name = m_genre);
    -- CHECK IF THE MOVIE EXISTS
	IF (movie_exists =0) THEN
		INSERT INTO movies(title,year, director, banner_url, trailer_url) 
        VALUES (m_title, m_year, m_director, m_banner_url, m_trailer_url);
        select * from movies;
        SET temp_movie_id = (SELECT id FROM movies WHERE title = m_title);
        -- CHECK IF THE STAR EXISTS
        IF (star_exists >0) THEN
			SET temp_star_id = (SELECT id FROM stars WHERE first_name = m_firstname and last_name=m_lastname);
            INSERT INTO stars_in_movies(star_id, movie_id) VALUES (temp_star_id, temp_movie_id);
		ELSE
			INSERT INTO stars(first_name, last_name) VALUES (m_firstname, m_lastname);
			SET temp_star_id = (SELECT id FROM stars WHERE first_name = m_firstname and last_name=m_lastname);
			INSERT INTO stars_in_movies(star_id, movie_id) VALUES (temp_star_id, temp_movie_id);
        END IF;
        -- NOW CHECK FOR THE GENRE
        IF (genre_exists>0) THEN
			SET temp_genre_id = (SELECT id FROM genres WHERE name = m_genre);
			INSERT INTO genres_in_movies(genre_id, movie_id) VALUES (temp_genre_id, temp_movie_id);
		ELSE
			INSERT INTO genres(name) VALUES (m_genre);
            SET temp_genre_id = (SELECT id FROM genres WHERE name = m_genre);
            INSERT INTO genres_in_movies(genre_id, movie_id) VALUES (temp_genre_id, temp_movie_id);
		END IF;
	ELSE -- This half is for inserting movie info in an existing movie
        SET temp_movie_id = (SELECT id FROM movies WHERE title = m_title);
        -- UPDATE YEAR IF NOT EMPTY
        IF (m_year!=0) THEN
			UPDATE movies SET year = m_year WHERE title=m_title;
		END IF;
        -- UPDATE DIRECTOR IF NOT EMPTY
		IF (m_director!='') THEN
			UPDATE movies SET director=m_director WHERE title=m_title;
		END IF;
        -- UPDATE BANNER URL IF NOT EMPTY
		IF (m_banner_url!='') THEN
			UPDATE movies SET banner_url= m_banner_url WHERE title = m_title;
		END IF;
        -- update trailer url if not empty
        IF (m_trailer_url!='') THEN
			UPDATE movies SET trailer_url= m_trailer_url WHERE title =m_title;
		END IF;
        
        IF (m_firstname != '' AND m_lastname != '') THEN
			IF (star_exists >0) THEN
				SET temp_star_id = (SELECT id FROM stars WHERE first_name = m_firstname and last_name=m_lastname);
				INSERT INTO stars_in_movies(star_id, movie_id) VALUES (temp_star_id, temp_movie_id);
			ELSE
				INSERT INTO stars(first_name, last_name) VALUES (m_firstname, m_lastname);
				SET temp_star_id = (SELECT id FROM stars WHERE first_name = m_firstname and last_name=m_lastname);
				INSERT INTO stars_in_movies(star_id, movie_id) VALUES (temp_star_id, temp_movie_id);
			END IF;
		END IF;
        -- NOW CHECK FOR THE GENRE
        IF (m_genre != '') THEN
			IF (genre_exists>0) THEN
				SET temp_genre_id = (SELECT id FROM genres WHERE name = m_genre);
				INSERT INTO genres_in_movies(genre_id, movie_id) VALUES (temp_genre_id, temp_movie_id);
			ELSE
				INSERT INTO genres(name) VALUES (m_genre);
				SET temp_genre_id = (SELECT id FROM genres WHERE name = m_genre);
				INSERT INTO genres_in_movies(genre_id, movie_id) VALUES (temp_genre_id, temp_movie_id);
			END IF;
		END IF;
	END IF;
      
END $$
 -- $$
 -- DELIMITER;
 


BEGIN TRANSACTION;

INSERT INTO comic (title, issue_title, issue_number, publisher, publish_date, hero, description, image_link)
VALUES ('Spawn', 'Questions Pt.1', 1, 'Image', '1992-05-01', 'Spawn', 'While a strange assailant stalks the city, ripping out human hearts, another otherworldly being arrives. As his mind reels, our tortured hero remembers that he struck a deal with the devil in order to return to his beloved wife - five years after his death. The man who was Al Simmons is no more. He has become Spawn.', 'https://comicvine1.cbsistatic.com/uploads/scale_large/5/56044/2466969-spawn__1992__001.jpeg');

INSERT INTO comic (title, issue_title, issue_number, publisher, publish_date, hero, description, image_link)
VALUES ('Batman/Grendel', 'Devils Mask', 2, 'DC', '1993-01-01', 'Batman', 'The true aggressive evil of Grendel is revealed as, in his malevolent quest to prove his superiority over Batman, he slaughters a squad of Gotham City cops and kidnaps an innocent child to use as a pawn in his cruel games of duplicity and murder. But the Dark Knight is not so easily duped, and Grendels extreme actions set them both up for a chilling confrontation!', 'https://comicvine1.cbsistatic.com/uploads/scale_large/6/67663/2658025-02.jpg');

INSERT INTO collection (name, description, private)
VALUES ('Spawn', 'Spawn Collection', false);

INSERT INTO collection (name, description, private)
VALUES ('Batman', 'Batman Collection', true);


INSERT INTO user_comic (comic_id, user_id, collection_id)
VALUES (1, 1, 1);

INSERT INTO user_comic (comic_id, user_id, collection_id)
VALUES (2,2 ,2 );

COMMIT TRANSACTION;

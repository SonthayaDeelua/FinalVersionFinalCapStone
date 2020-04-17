BEGIN TRANSACTION;

INSERT INTO users (username, email, password, salt, role)
VALUES ('Rose Madder', 'rm@gmail.com', 'og5oyJKVhXatoiMb6iO7Pg==', 'xnblsj70Y0vJhOrie3T/bSKpZBUQrfUG0fbzLyIHNIuvxv9IwN4VFMKiIs4lxo6wVnILelkOGp2oKwGlx34vilvec4G5anFieBzZMPeave+KgyD3iuLt6isGacn7W4O/SjJmaOoTTY2t5UGZLXB2dxefKUiL/z3DdBWp2jvsH6I=', 'premium');

INSERT INTO users (username, email, password, salt, role)
VALUES ('Hank Williams', 'notinblack@gmail.com','Y5x5wXC+KLM+X762XL7a5w==', 'BWkE9n6Ijw48tNb4cKSDxS+UY9BW5rsFQGhBaXn16RAsgtyjw3D70A1narLw3xzFASQ4Lp+bUx7mTaUm6POSAhuZSDhYZfXBUeZ6U4AOM1rSlo5nir1NOeYTcu662Zka33WpSoz0VQkgketEE0eIPiB0m9sYymxqDFLZa9np0Kw=', 'premium');

INSERT INTO users (username, password, salt, role)
VALUES ('Frank Riverton', 'sJpeq36b+IkbEOscjkCDOQ==', 'N767VZ+NBB5AN3CXRz/BygZokjdO53qWk0h67nFm/JACMzpzioAln1VQLIV4dYGp41cRUWh+F8Baw98TGDBCDFrQ/2sQR7FvD4bcCFR3jklmcymyDkZZVxL+Lftw0VOpdg4pAGHLaLV1BG8m4etjh86zH6/TJ3srei9s8qJWFu0=', 'user');


INSERT INTO comic (title,  issue_title, issue_number, publisher, publish_date, hero, description, image_link)
VALUES ('Spawn',  'Questions Pt.1', 1, 'Image', '1992-05-01', 'Spawn', 'While a strange assailant stalks the city, ripping out human hearts, another otherworldly being arrives. As his mind reels, our tortured hero remembers that he struck a deal with the devil in order to return to his beloved wife - five years after his death. The man who was Al Simmons is no more. He has become Spawn.', 'https://comicvine1.cbsistatic.com/uploads/scale_large/5/56044/2466969-spawn__1992__001.jpeg');

INSERT INTO comic (title, issue_title, issue_number, publisher, publish_date, hero, description, image_link)
VALUES ('Spawn',  'Questions Pt.2', 2, 'Image', '1993-01-01', 'Spawn', 'Spawn decides to find Wanda, but fears her reaction to what hes become. With his new powers, he attempts to change his ravaged features, with disappointing results. Exhausted, he collapses, only to be taunted by a foul-mouthed dwarf in clown makeup.</p><h2><b>Spoilers</b></h2><p>This comic opens with Violator having a lil one on one talk with himself showing off his crude like personality. As it continues spawn is still searching for himself trying to figure out who exactly he is and where his wife is! the comic still in almost a prelude like stage, with a lil more action than the first thanks to the now present violator. well not to give off too much the comic ends with a cliff hanger involving Spawn and Violator!', 'https://comicvine1.cbsistatic.com/uploads/scale_large/6/67663/2658025-02.jpg');

INSERT INTO comic (title,  issue_title, issue_number, publisher, publish_date, hero, description, image_link)
VALUES ('Batman','Ace, the Bat-Hound', 92, 'DC Comics', '1955-06-01', 'Batman', 'Stories: Fan-Mail of Danger, Batmans Guilty Neighbor, Ace the Bat-Hound', 'https://comicvine1.cbsistatic.com/uploads/original/0/4/1683-796-1815-1-batman.jpg');

INSERT INTO collection (name, user_id, description, private)
VALUES ('Spawn', 1, 'Spawn Collection', false);

INSERT INTO collection (name, user_id, description, private)
VALUES ('Batman', 2, 'Batman Collection', false);


INSERT INTO user_comic (comic_id, user_id, collection_id)
VALUES (1, 1, 1);

INSERT INTO user_comic (comic_id, user_id, collection_id)
VALUES (2, 1, 1);

INSERT INTO user_comic (comic_id, user_id, collection_id)
VALUES (3,2 ,2 );


COMMIT TRANSACTION;
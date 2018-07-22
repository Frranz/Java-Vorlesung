The following program lets you view movies from the IMdB movie-database and filter the outputs by certain parameters.

There's a static and an interactive mode.

In the interactive mode you can 

1) rate movies
2) get movie suggestions
3) search for movies
4) show your own ratings

in the static mode you have to pass the arguments via command line.
The static mode returns a line each for each movie with its id, imdb rating, title, genre and directors

The folowing arguments are valid: 

--actor="aName,bName,cName"
--genre="aGenre,bGenre,cGenre"
--director="aDirector,bDirector,cDirector"
--film="aTitle,bTitle,cTitle" 			=> gets Movies which others have also rated (sorted by how well they rated them)
--limit=20                   			=> limits the amount of outputted movies. default: 200

--test=true					=> starts the testmode, which makes 3 basic request and writes their output to results.txt
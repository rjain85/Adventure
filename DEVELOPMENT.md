# Adventure2
Text Adventure Assignment Extended

I plan to:

- Improve my Layout:
  - remove unnecessary parentheses around my String declarations
  - delete the commented out code
  - remove unnecessary whitespace
- Improve my Naming:
  - name boolean methods as questions
  - check that all objects are named as nouns
  - rename scanner object to inputScanner
  - rename create...Message to print...Message
- Improve Overall Design:
  - remove if statements from some boolean methods and just return boolean expressions
  - create a separate class for JSON parsing
  
Plan for new assignment:

- Create a .json file mimicking structure of Hogwarts School from Harry Potter
- Create an Item class to parse Items from Hogwarts.json. 
- Create player options in Hogwarts.json
  - create a Player class to parse player options. Players will have different abilities, which are stored as Item objects
- Add a boolean "hidden" to .json to allow some rooms to be hidden from player.
  - check for hidden in createOptions method of Room class.
- Update loopThroughGame in Adventure class so that it takes user input on items and checks for validKeyNames.
  - add helper method to let user choose player.
  - add other helper methods to let user collect objects.
  
Issues:

- Several nullPointerExceptions from errors and typos made in creating the .json.


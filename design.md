# Design Document
In this document the general design of the app will be discussed.

## Diagram
![Visual diagram](/doc/diagram.png "Visual diagram")
This diagram shows the structure of the app.

## Api's, frameworks, plugins
- SQLLite for a database of songs and playlists
- MediaPlayer and Service for allowing playback in the background
- ContentResolver to scan for music files
- Entagged (http://entagged.sourceforge.net/developer.php) to read and process ID3 tags

## Data sources
The main data source is going to be the ContentResolver which will scan
the filesystem for music files to play. These media files will be added to
the SQLLite database to allow for easy searching and filtering for the smart playlists.

## Database structure
The database will consist of two tables: one table of songs and another one containing playlists.


### Songs
| Field name | Type       |
|------------|------------|
| filename   | VARCHAR 30 |
| title      | VARCHAR 30 |
| artist     | VARCHAR 30 |
| album      | VARCHAR 30 |
| year       | INTEGER    |
| comment    | VARCHAR 30 |
| genre      | VARCHAR 30 |
| rating     | INTEGER    |

### Playlists
| Field name        | Type       |
|-------------------|------------|
| name              | VARCHAR 30 |
| condition_1_type  | VARCHAR 30 |
| condition_1_field | VARCHAR 30 |
| condition_1_value | VARCHAR 30 |
| condition_2_type  | VARCHAR 30 |
| condition_2_field | VARCHAR 30 |
| condition_2_value | VARCHAR 30 |
| condition_3_type  | VARCHAR 30 |
| condition_3_field | VARCHAR 30 |
| condition_3_value | VARCHAR 30 |
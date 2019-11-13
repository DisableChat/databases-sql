/******************************************************************************
 * Query #1
 * Description: First query is joining tables summoner, riot_reward, and play
 *              to find the number one ranked Challenger player and return the
 *              name of that player and the champions he or she plays. For
 *              anoyone not familiar with these terms, it basically means to
 *              search for the number one player in a game (this case League
 *              of Legends) and return that player and the characters (champions)
 *              that player plays ingame. This is usefull to see what really
 *              good players use in game so one can try and play those champs
 *              as well.
 ******************************************************************************
 */

select  Summoner_name, Champion_n
from    summoner, riot_reward, play
where   Division='Challenger'
        And Challenger_border='001'
        AND Summoner_name=Summoner_n
        AND Summoner_name=SumName;

/******************************************************************************
 * Query #2
 * Description: The second query is just displaying the rune page id number,
 *              the name of that page, and the two keystones that user has for
 *              that given runeset. In this case the player is Faker.
 *              In plain english or less league of legends terms, this means
 *              I am looking up a user in the database who goes by Faker and
 *              displaying the id number for that page, the name the player gives
 *              the page, and the two keystones that go along with the page
 *              (ingame stats/abilities that are determined by the specific
 *              keystones). Page in this case is just an in game customizable
 *              tree - league of legends calls it a runepage.
 ******************************************************************************
 */
select  Rune_id_no, name, Keystone, adapt_keystone
from    rune_page
where   Sname='Faker';

/******************************************************************************
 * Query #3
 * Description: The third query is just returning the champion names in the db
 *              (characters a user can play) of a specific possition in this
 *              case the JNG role. In league of legends users have a possition
 *              they play on the map - five in total (top, jungle, mid, adc, or
 *              supp).
 ******************************************************************************
 */
select  Champion_name
from    champion
where   possition='JNG';

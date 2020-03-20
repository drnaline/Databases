/* NOTICE: All materials provided by this project, and materials derived 
 * from the project, are the property of the University of Texas. 
 * Project materials, or those derived from the materials, cannot be placed 
 * into publicly accessible locations on the web. Project materials cannot 
 * be shared with other project teams. Making project materials publicly 
 * accessible, or sharing with other project teams will result in the 
 * failure of the team responsible and any team that uses the shared materials. 
 * Sharing project materials or using shared materials will also result 
 * in the reporting of all team members for academic dishonesty. 
 */
package cs4347.jdbcGame.entity;

import java.util.Date;

public class GamesOwned
{
    private Long id;
    private Long playerID; // NOTE: This should be reference to Player, not ID
    private Long gameID; // NOTE: This should be reference to Game, not ID
    private Date purchaseDate;
    private float purchasePrice;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getPlayerID()
    {
        return playerID;
    }

    public void setPlayerID(Long playerID)
    {
        this.playerID = playerID;
    }

    public Long getGameID()
    {
        return gameID;
    }

    public void setGameID(Long gameID)
    {
        this.gameID = gameID;
    }

    public Date getPurchaseDate()
    {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate)
    {
        this.purchaseDate = purchaseDate;
    }

    public float getPurchasePrice()
    {
        return purchasePrice;
    }

    public void setPurchasePrice(float purchasePrice)
    {
        this.purchasePrice = purchasePrice;
    }

}

package com.example.monopoly.gamelogic;

import android.util.Log;

import com.example.monopoly.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton Object
 */
public class Game{
    private static final Game OBJ = new Game();
    private HashMap<Integer,Player> players;        //ID,Player ... ID will be set server-side
    private HashMap<Integer,Field> fields;

    private String currentPlayersTurn;
    private static final AtomicInteger count = new AtomicInteger(0);

    private Game() {
        players = new HashMap<>();
    }

    public static Game getInstance() {
        return OBJ;
    }

    /***
     * Adds the player to the Hashmap + Auto-Increment-ID
     * @return
     * true on success, false otherwise
     */
    public boolean addPlayer(Player player){
        if(players.containsValue(player))
            return false;
        int id = count.getAndIncrement();
        players.put(id,player);
        player.setId(id);
        // TODO Are there any better implementations?
        return true;
    }

    public HashMap<Integer, Player> getPlayers() {
        return players;
    }
    public void incrementPlayerPosition(int id,int incr){
        Log.i("Dices","Player:"+this.players.get(id).getUsername()+"; Pos to increment:"+incr+"; Current Pos:"+this.players.get(id).getPosition());
        this.players.get(id).incrementPosition(incr);
    }

    public int getPlayerIDByName(String userName){
        for(int i = 0; i < this.players.size(); i++){
            if(userName.equals(this.players.get(i).getUsername())){
                return i;
            }
        }
        return 0;
    }

    public String getCurrentPlayersTurn() {
        return currentPlayersTurn;
    }

    public void setCurrentPlayersTurn(String currentPlayersTurn) {
        this.currentPlayersTurn = currentPlayersTurn;
    }


    public void doAction(int playerID) throws IOException {

        //Advance to "Go".
        if (players.get(playerID).getCardID() == R.drawable.chance0 || players.get(playerID).getCardID() == R.drawable.community0) {
            int incr = fields.size() - players.get(playerID).getPosition();
            moveProtocol(playerID, incr);
            endTurnProtocol(playerID);
        }

        //Advance to Strandbad. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == R.drawable.chance1) {
            advanceAndCollect(playerID, "Strandbad");
        }

        //Advance to Lindwurm. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == R.drawable.chance2) {
            advanceAndCollect(playerID, "Lindwurm");
        }

        //Your building loan matures. Receive $150.
        if (players.get(playerID).getCardID() == R.drawable.chance3) {
            transferToPlayerProtocol(playerID, 150);
            endTurnProtocol(playerID);
        }

        //Advance to the nearest Railroad. If unowned, you may buy it from the Bank.
        // If owned, pay owner twice the rental to which they are otherwise entitled.
        if (players.get(playerID).getCardID() == R.drawable.chance4) {
            int kaernten = 0;
            int tirol = 0;
            int steiermark = 0;
            int wien = 0;

            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i).getName() == "S-Bahn Kärnten") {
                    kaernten = fields.get(i).getId();
                }
                if (fields.get(i).getName() == "S-Bahn Tirol") {
                    tirol = fields.get(i).getId();
                }
                if (fields.get(i).getName() == "S-Bahn Steiermark") {
                    steiermark = fields.get(i).getId();
                }
                if (fields.get(i).getName() == "S-Bahn Wien") {
                    wien = fields.get(i).getId();
                }
            }

            int difKtn = kaernten - players.get(playerID).getPosition();
            int difTrl = tirol - players.get(playerID).getPosition();
            int difStm = steiermark - players.get(playerID).getPosition();
            int difVie = wien - players.get(playerID).getPosition();

            int incr = Math.abs(difKtn);

            if (Math.abs(difTrl) < incr) {
                incr = difTrl;
            }
            if (Math.abs(difStm) < incr) {
                incr = difStm;
            }
            if (Math.abs(difVie) < incr) {
                incr = difVie;
            }

            int amount;
            Player owner;
            moveProtocol(playerID, incr);
            if (fields.get(players.get(playerID).getPosition()).getOwner() != null) {
                amount = fields.get(players.get(playerID).getPosition()).getCost() * 2;
                owner = fields.get(players.get(playerID).getPosition()).getOwner();

                transferPlayerToPlayerProtocol(playerID, owner.getId(), amount);
            }
            endTurnProtocol(playerID);
        }

        //Bank pays you dividend of $50.
        if (players.get(playerID).getCardID() == R.drawable.chance5) {
            transferToPlayerProtocol(playerID, 50);
            endTurnProtocol(playerID);
        }

        //Get out of Jail Free.
        if (players.get(playerID).getCardID() == R.drawable.chance6 || players.get(playerID).getCardID() == R.drawable.chance12 || players.get(playerID).getCardID() == R.drawable.community4) {
            outOfJailProtocol(playerID, 1);
            endTurnProtocol(playerID);
        }

        //Go back 3 spaces.
        if (players.get(playerID).getCardID() == R.drawable.chance7) {
            moveProtocol(playerID, -3);
        }

        //Make general repairs on all your property: For each house pay $25, for each hotel pay $100.
        if (players.get(playerID).getCardID() == R.drawable.chance8) {
            //TODO
        }

        //Take a trip to S-Bahn Wien.
        if (players.get(playerID).getCardID() == R.drawable.chance9) {
            int incr;
            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i).getName() == "S-Bahn Wien") {
                    incr = players.get(playerID).getPosition() - fields.get(i).getId();
                    moveProtocol(playerID, incr);
                }
            }
        }

        //You have been elected Chairman of the Board. Pay each player $50.
        if (players.get(playerID).getCardID() == R.drawable.chance10) {
            for (int i = 0; i < players.size(); i++) {
                if (i != playerID) {
                    transferPlayerToPlayerProtocol(playerID, i, 50);
                }
                endTurnProtocol(playerID);
            }
        }

        //Advance to City Arkaden. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == R.drawable.chance11) {
            advanceAndCollect(playerID, "City Arkaden");
        }

        //Go to Jail directly.
        if (players.get(playerID).getCardID() == R.drawable.chance13 || players.get(playerID).getCardID() == R.drawable.chance16 || players.get(playerID).getCardID() == R.drawable.community5) {
            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i).getName() == "Jail") {
                    int incr = fields.get(i).getId() - players.get(playerID).getPosition();
                    moveProtocol(playerID, incr);
                }
            }
            endTurnProtocol(playerID);
        }

        //Parking Ticket! Pay $15.
        if (players.get(playerID).getCardID() == R.drawable.chance14 || players.get(playerID).getCardID() == R.drawable.community14) {
            transferToBankProtocol(playerID, 15);
            endTurnProtocol(playerID);
        }

        //Advance two spaces.
        if (players.get(playerID).getCardID() == R.drawable.chance15) {
            moveProtocol(playerID, 2);
        }

        //Happy Birthday! Receive $100.
        if (players.get(playerID).getCardID() == R.drawable.chance17) {
            transferToPlayerProtocol(playerID, 100);
            endTurnProtocol(playerID);
        }

        //Go back one space.
        if (players.get(playerID).getCardID() == R.drawable.chance18) {
            moveProtocol(playerID, -1);
        }

        //Advance to Rathaus. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == R.drawable.chance19) {
            advanceAndCollect(playerID, "Rathaus");
        }

        //Bank error in your favor. Collect $200.
        if (players.get(playerID).getCardID() == R.drawable.community1) {
            transferToPlayerProtocol(playerID, 200);
            endTurnProtocol(playerID);
        }

        //Doctor’s fee. Pay $50.
        if (players.get(playerID).getCardID() == R.drawable.community2) {
            transferToBankProtocol(playerID, 50);
            endTurnProtocol(playerID);
        }

        //From sale of stock you receive $50.
        if (players.get(playerID).getCardID() == R.drawable.community3) {
            transferToPlayerProtocol(playerID, 50);
            endTurnProtocol(playerID);
        }

        //community6: Holiday fund matures. Receive $100.
        if (players.get(playerID).getCardID() == R.drawable.community6) {
            transferToPlayerProtocol(playerID, 100);
            endTurnProtocol(playerID);
        }

        //Income tax refund. Collect $20.
        if (players.get(playerID).getCardID() == R.drawable.community7) {
            transferToPlayerProtocol(playerID, 20);
            endTurnProtocol(playerID);
        }

        //It is your birthday. Collect $10 from every player.
        if (players.get(playerID).getCardID() == R.drawable.community8) {
            for (int i = 0; i < players.size(); i++) {
                if (i != playerID) {
                    transferPlayerToPlayerProtocol(i, playerID, 10);
                }
            }
            endTurnProtocol(playerID);
        }

        //Life insurance matures. Collect $100.
        if (players.get(playerID).getCardID() == R.drawable.community9) {
            transferToPlayerProtocol(playerID, 100);
            endTurnProtocol(playerID);
        }

        //Pay hospital fees of $100.
        if (players.get(playerID).getCardID() == R.drawable.community10) {
            transferToBankProtocol(playerID, 100);
            endTurnProtocol(playerID);
        }

        //Pay school fees of $50.
        if (players.get(playerID).getCardID() == R.drawable.community11) {
            transferToBankProtocol(playerID, 50);
            endTurnProtocol(playerID);
        }

        //Receive $25 consultancy fee.
        if (players.get(playerID).getCardID() == R.drawable.community12) {
            transferToPlayerProtocol(playerID, 25);
            endTurnProtocol(playerID);
        }

        //You are assessed for street repair. $40 per house. $115 per hotel.
        if (players.get(playerID).getCardID() == R.drawable.community13) {
            /*int counterHotel = 0;
            int counterHouse = 0;

            for(int i = 0; i<fields.size(); i++){
                if (fields.get(i).getOwner().getId() == playerID){
                    counterHouse += fields.get(i).getHouses();
                }
            }*/
            //TODO
        }

        //You have won second prize in a beauty contest. Collect $10.
        if (players.get(playerID).getCardID() == R.drawable.community15) {
            transferToPlayerProtocol(playerID, 10);
            endTurnProtocol(playerID);
        }

        //You inherit $100.
        if (players.get(playerID).getCardID() == R.drawable.community16) {
            transferToBankProtocol(playerID, 100);
            endTurnProtocol(playerID);
        }

        //You receive $50 from warehouse sales.
        if (players.get(playerID).getCardID() == R.drawable.community17) {
            transferToPlayerProtocol(playerID, 50);
            endTurnProtocol(playerID);
        }

        //You receive a 7% dividend on preferred stock: $25.
        if (players.get(playerID).getCardID() == R.drawable.community18) {
            transferToPlayerProtocol(playerID, 25);
            endTurnProtocol(playerID);
        }

        //Receive $100 compensation for pain and suffering from the next player.
        if (players.get(playerID).getCardID() == R.drawable.community19) {
            transferPlayerToPlayerProtocol(playerID + 1, playerID, 100);
            endTurnProtocol(playerID);
        }

    }

    public void advanceAndCollect(int playerID, String location) throws IOException {
        int fieldId = 0;
        int incr;
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).getName() == location) {
                fieldId = fields.get(i).getId();
            }
        }
        if (players.get(playerID).getPosition() > fieldId) {
            incr = fields.size() - players.get(playerID).getPosition() + fieldId;
            transferToPlayerProtocol(playerID, 200);
        } else {
            incr = fieldId - players.get(playerID).getPosition();
        }
        moveProtocol(playerID, incr);
        if (players.get(playerID).getPosition() == 0) {
            endTurnProtocol(playerID);
        }
    }

    public void transferToPlayerProtocol(int playerID, int amount) throws IOException {
        players.get(playerID).getMyClient().writeToServer("GameBoardUI|transferToPlayer|" + amount + "|" + players.get(playerID).getUsername());
    }

    public void transferToBankProtocol(int playerID, int amount) throws IOException {
        players.get(playerID).getMyClient().writeToServer("GameBoardUI|transferToBank|" + amount + "|" + players.get(playerID).getUsername());
    }

    public void transferPlayerToPlayerProtocol(int senderID, int receiverID, int amount) throws IOException {
        players.get(senderID).getMyClient().
                writeToServer("GameBoardUI|transferPlayerToPlayer|" + players.get(receiverID) + ":" + amount + "|" + players.get(senderID).getUsername());
    }

    public void moveProtocol(int playerID, int incr) throws IOException {
        players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|" + incr + "|" + players.get(playerID).getUsername());
    }

    public void outOfJailProtocol(int playerID, int amount) throws IOException {
        players.get(playerID).getMyClient().writeToServer("GameBoardUI|outOfJail|" + amount + "|" + players.get(playerID).getUsername());
    }

    public void endTurnProtocol(int playerID) throws IOException {
        players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn|" + players.get(playerID).getUsername());
    }

}

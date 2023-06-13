package com.example.monopoly.gamelogic;

import android.util.Log;
import com.example.monopoly.R;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    //private volatile int ids = 0;

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

    public void setFields(HashMap<Integer, Field> fields) {
        this.fields = fields;
    }

    public void doAction() throws IOException {

        int playerID = getPlayerIDByName(getCurrentPlayersTurn());

        //Advance to "Go".
        if (players.get(playerID).getCardID() == R.drawable.chance0 || players.get(playerID).getCardID() == R.drawable.community0) {
            int incr = fields.size() - players.get(playerID).getPosition();
            moveProtocol(incr);
            endTurnProtocol();
        }

        //Advance to Strandbad. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == R.drawable.chance1) {
            advanceAndCollect("Strandbad");
        }

        //Advance to Lindwurm. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == R.drawable.chance2) {
            advanceAndCollect("Lindwurm");
        }

        //Your building loan matures. Receive $150.
        if (players.get(playerID).getCardID() == R.drawable.chance3) {
            transferToPlayerProtocol(150);
            endTurnProtocol();
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
            if (fields.get(players.get(playerID).getPosition() + incr).getOwner() != null) {
                amount = fields.get(players.get(playerID).getPosition()).getCost() * 2;
                owner = fields.get(players.get(playerID).getPosition() + incr).getOwner();

                transferPlayerToPlayerProtocol(owner.getId(), amount);
            }
            moveProtocol(incr);
        }

        //Bank pays you dividend of $50.
        if (players.get(playerID).getCardID() == R.drawable.chance5) {
            transferToPlayerProtocol(50);
            endTurnProtocol();
        }

        //Get out of Jail Free.
        if (players.get(playerID).getCardID() == R.drawable.chance6 || players.get(playerID).getCardID() == R.drawable.chance12 || players.get(playerID).getCardID() == R.drawable.community4) {
            outOfJailCounterProtocol(1);
            endTurnProtocol();
        }

        //Go back 3 spaces.
        if (players.get(playerID).getCardID() == R.drawable.chance7) {
            moveProtocol(-3);
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
                    moveProtocol(incr);
                }
            }
        }

        //You have been elected Chairman of the Board. Pay each player $50.
        if (players.get(playerID).getCardID() == R.drawable.chance10) {
            for (int i = 0; i < players.size(); i++) {
                if (i != playerID) {
                    transferPlayerToPlayerProtocol(i, 50);
                }
                endTurnProtocol();
            }
        }

        //Advance to City Arkaden. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == R.drawable.chance11) {
            advanceAndCollect("City Arkaden");
        }

        //Go to Jail directly.
        if (players.get(playerID).getCardID() == R.drawable.chance13 || players.get(playerID).getCardID() == R.drawable.chance16 || players.get(playerID).getCardID() == R.drawable.community5) {
            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i).getName() == "Jail") {
                    int incr = fields.get(i).getId() - players.get(playerID).getPosition();
                    moveProtocol(incr);
                }
            }
            endTurnProtocol();
        }

        //Parking Ticket! Pay $15.
        if (players.get(playerID).getCardID() == R.drawable.chance14 || players.get(playerID).getCardID() == R.drawable.community14) {
            transferToBankProtocol(15);
            endTurnProtocol();
        }

        //Advance two spaces.
        if (players.get(playerID).getCardID() == R.drawable.chance15) {
            moveProtocol(2);
        }

        //Happy Birthday! Receive $100.
        if (players.get(playerID).getCardID() == R.drawable.chance17) {
            transferToPlayerProtocol(100);
            endTurnProtocol();
        }

        //Go back one space.
        if (players.get(playerID).getCardID() == R.drawable.chance18) {
            moveProtocol(-1);
        }

        //Advance to Rathaus. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == R.drawable.chance19) {
            advanceAndCollect("Rathaus");
        }

        //Bank error in your favor. Collect $200.
        if (players.get(playerID).getCardID() == R.drawable.community1) {
            transferToPlayerProtocol(200);
            endTurnProtocol();
        }

        //Doctor’s fee. Pay $50.
        if (players.get(playerID).getCardID() == R.drawable.community2) {
            transferToBankProtocol(50);
            endTurnProtocol();
        }

        //From sale of stock you receive $50.
        if (players.get(playerID).getCardID() == R.drawable.community3) {
            transferToPlayerProtocol(50);
            endTurnProtocol();
        }

        //community6: Holiday fund matures. Receive $100.
        if (players.get(playerID).getCardID() == R.drawable.community6) {
            transferToPlayerProtocol(100);
            endTurnProtocol();
        }

        //Income tax refund. Collect $20.
        if (players.get(playerID).getCardID() == R.drawable.community7) {
            transferToPlayerProtocol(20);
            endTurnProtocol();
        }

        //It is your birthday. Collect $10 from every player.
        if (players.get(playerID).getCardID() == R.drawable.community8) {
            for (int i = 0; i < players.size(); i++) {
                if (i != playerID) {
                    transferPlayerToPlayerProtocol(i, 10);
                }
            }
            endTurnProtocol();
        }

        //Life insurance matures. Collect $100.
        if (players.get(playerID).getCardID() == R.drawable.community9) {
            transferToPlayerProtocol(100);
            endTurnProtocol();
        }

        //Pay hospital fees of $100.
        if (players.get(playerID).getCardID() == R.drawable.community10) {
            transferToBankProtocol(100);
            endTurnProtocol();
        }

        //Pay school fees of $50.
        if (players.get(playerID).getCardID() == R.drawable.community11) {
            transferToBankProtocol(50);
            endTurnProtocol();
        }

        //Receive $25 consultancy fee.
        if (players.get(playerID).getCardID() == R.drawable.community12) {
            transferToPlayerProtocol(25);
            endTurnProtocol();
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
            transferToPlayerProtocol(10);
            endTurnProtocol();
        }

        //You inherit $100.
        if (players.get(playerID).getCardID() == R.drawable.community16) {
            transferToBankProtocol(100);
            endTurnProtocol();
        }

        //You receive $50 from warehouse sales.
        if (players.get(playerID).getCardID() == R.drawable.community17) {
            transferToPlayerProtocol(50);
            endTurnProtocol();
        }

        //You receive a 7% dividend on preferred stock: $25.
        if (players.get(playerID).getCardID() == R.drawable.community18) {
            transferToPlayerProtocol(25);
            endTurnProtocol();
        }

        //Receive $100 compensation for pain and suffering from the next player.
        if (players.get(playerID).getCardID() == R.drawable.community19) {
            transferPlayerToPlayerProtocol(playerID + 1, 100);
            endTurnProtocol();
        }

    }

    public void advanceAndCollect(String location) throws IOException {
        int playerID = getPlayerIDByName(currentPlayersTurn);
        int fieldId = 0;
        int incr;

        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).getName().equals(location)) {
                fieldId = fields.get(i).getId();
            }
        }

        if (players.get(playerID).getPosition() > fieldId) {
            incr = fields.size() - players.get(playerID).getPosition() + fieldId;
            transferToPlayerProtocol(200);
        } else {
            incr = fieldId - players.get(playerID).getPosition();
        }
        moveProtocol(incr);

        if (players.get(playerID).getPosition() == 0) {
            endTurnProtocol();
        }

    }

    public void transferToPlayerProtocol(int amount) throws IOException {
        int playerID = getPlayerIDByName(getCurrentPlayersTurn());
        getPlayers().get(playerID).getMyClient().writeToServer("GameBoardUI|transferToPlayer|" + amount + "|" + getCurrentPlayersTurn());
    }

    public void transferToBankProtocol(int amount) throws IOException {
        int playerID = getPlayerIDByName(currentPlayersTurn);
        players.get(playerID).getMyClient().writeToServer("GameBoardUI|transferToBank|" + amount + "|" + currentPlayersTurn);
    }

    public void transferPlayerToPlayerProtocol(int receiverID, int amount) throws IOException {
        int playerID = getPlayerIDByName(currentPlayersTurn);
        players.get(playerID).getMyClient().
                writeToServer("GameBoardUI|transferPlayerToPlayer|" + players.get(receiverID).getId() + ":" + amount + "|" + currentPlayersTurn);
    }

    public void moveProtocol(int incr) throws IOException {
        int playerID = getPlayerIDByName(currentPlayersTurn);
        players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|" + incr + "|" + currentPlayersTurn);
    }

    public void outOfJailCounterProtocol(int amount) throws IOException {
        int playerID = getPlayerIDByName(currentPlayersTurn);
        players.get(playerID).getMyClient().writeToServer("GameBoardUI|outOfJailCounter|" + amount + "|" + currentPlayersTurn);
    }

    public void endTurnProtocol() throws IOException {
        int playerID = getPlayerIDByName(currentPlayersTurn);
        players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn|" + currentPlayersTurn);
    }

}

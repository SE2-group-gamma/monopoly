package com.example.monopoly.gamelogic;

import android.graphics.Color;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
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

    public int advanceAndCollect(String location, int playerID){
        int fieldId = 0;
        int incr;
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).getName() == location) {
                fieldId = fields.get(i).getId();
            }
        }
        if (players.get(playerID).getPosition() > fieldId) {
            incr = fields.size() - players.get(playerID).getPosition() + fieldId;
        }else {
            incr = fieldId - players.get(playerID).getPosition();
        }
        return incr;
    }

    public void doAction(int playerID) throws IOException {

        //chance0, community0: Advance to "Go".
        if (players.get(playerID).getCardID() == 2131165320 || players.get(playerID).getCardID() == 2131165341) {
            int incr = fields.size() - players.get(playerID).getPosition();
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|" + incr +"|" + players.get(playerID).getUsername());
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn|" + players.get(playerID).getUsername());
        }

        //chance1: Advance to Strandbad. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == 2131165321) {
            int incr = advanceAndCollect("Strandbad", playerID);

            players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|" + incr + "|" + players.get(playerID).getUsername());
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn|" + players.get(playerID).getUsername());
        }

        //chance2: Advance to Lindwurm. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == 2131165332) {
            int incr = advanceAndCollect("Lindwurm", playerID);

            players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|" + incr + "|" + players.get(playerID).getUsername());
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn|" + players.get(playerID).getUsername());
        }

        //chance3, Your building loan matures. Receive $150.
        if (players.get(playerID).getCardID() == 2131165333) {
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|transferMoney|150" + players.get(playerID).getUsername());
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn|" + players.get(playerID).getUsername());
        }

        //chance4: Advance to the nearest Railroad. If unowned, you may buy it from the Bank.
        // If owned, pay owner twice the rental to which they are otherwise entitled.
        if (players.get(playerID).getCardID() == 2131165334) {
            int kaernten = 0;
            int tirol = 0;
            int steiermark = 0;
            int wien = 0;
            int incr;

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

            //TODO: check
            if (Math.abs(difKtn) < Math.abs(difTrl)) {
                if (Math.abs(difKtn) < Math.abs(difStm)) {
                    if (Math.abs(difKtn) < Math.abs(difVie)) {
                        incr = difKtn;
                    } else {
                        incr = difVie;
                    }
                } else {
                    if (Math.abs(difStm) < Math.abs(difVie)) {
                        incr = difStm;
                    } else {
                        incr = difVie;
                    }
                }
            } else {
                if (Math.abs(difTrl) < Math.abs(difStm)) {
                    if (Math.abs(difTrl) < Math.abs(difVie)) {
                        incr = difTrl;
                    } else {
                        incr = difVie;
                    }
                } else {
                    if (Math.abs(difTrl) < Math.abs(difVie)) {
                        incr = difTrl;
                    } else {
                        incr = difVie;
                    }
                }
            }

            int amount;
            Player owner;
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|" + incr + "|" + players.get(playerID).getUsername());
            if (fields.get(players.get(playerID).getPosition()).getOwner() != null) {
                amount = fields.get(players.get(playerID).getPosition()).getCost() * 2;
                owner = fields.get(players.get(playerID).getPosition()).getOwner();

                //[Fragment]|transferPlayerToPlayer|[receiverID]:[amount]|[senderUserName]
                players.get(playerID).getMyClient().
                        writeToServer("GameBoardUI|transferPlayerToPlayer|" + owner.getId() + ":" + amount + "|" + players.get(playerID).getUsername());
            }

            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn|" + players.get(playerID).getUsername());
        }

        //chance5: Bank pays you dividend of $50.
        if (players.get(playerID).getCardID() == 2131165335) {
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|transferMoney|50" + players.get(playerID).getUsername());
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn|" + players.get(playerID).getUsername());
        }

        //chance6, chance12, community4: Get out of Jail Free.
        if (players.get(playerID).getCardID() == 2131165336 || players.get(playerID).getCardID() == 2131165324 || players.get(playerID).getCardID() == 2131165355)  {
            //TODO: add attribute boolean outOfJailFree to player?
        }

        //chance7: Go back 3 spaces.
        if (players.get(playerID).getCardID() == 2131165337) {
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|-3|" + players.get(playerID).getUsername());
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn");
        }

        //chance8: Make general repairs on all your property: For each house pay $25, for each hotel pay $100.
        if (players.get(playerID).getCardID() == 2131165338) {
            //TODO: everything
        }

        //chance9: Take a trip to S-Bahn Wien.
        if (players.get(playerID).getCardID() == 2131165339) {
            int incr;
            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i).getName() == "S-Bahn Wien") {
                    incr = players.get(playerID).getPosition() - fields.get(i).getId();
                    players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|" + incr + "|" + players.get(playerID).getUsername());
                }
            }
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn|" + players.get(playerID).getUsername());
        }

        //chance10, You have been elected Chairman of the Board. Pay each player $50.
        if (players.get(playerID).getCardID() == 2131165322) {
            for(int i =0; i< players.size(); i++){
                if (i != playerID){
                    players.get(playerID).getMyClient().
                            writeToServer("GameBoardUI|transferPlayerToPlayer|" + players.get(playerID) + ":50|" + players.get(playerID).getUsername());
                }
                players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn|" + players.get(playerID).getUsername());
            }
        }

        //chance11: Advance to City Arkaden. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == 2131165323) {
            int incr = advanceAndCollect("City Arkaden", playerID);

            players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|" + incr + "|" + players.get(playerID).getUsername());
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn|" + players.get(playerID).getUsername());
        }

        //chance13, chance16, community5: Go to Jail directly.
        if (players.get(playerID).getCardID() == 2131165325 || players.get(playerID).getCardID() == 2131165328 || players.get(playerID).getCardID() == 2131165356) {
            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i).getName() == "Jail") {
                    int incr = fields.get(i).getId() - players.get(playerID).getPosition();
                    players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|" + incr + "|" + players.get(playerID).getUsername());
                }
            }
        }

        //chance14, Parking Ticket! Pay $15.
        if (players.get(playerID).getCardID() == 2131165326) {
            //TODO: add action "transferToBank" to Client?
            players.get(playerID).setCapital(players.get(playerID).getCapital() - 15);
        }

        //chance15: Advance two spaces.
        if (players.get(playerID).getCardID() == 2131165327) {
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|+2|" + players.get(playerID).getUsername());
        }

        //chance17: Happy Birthday! Receive $100.
        if (players.get(playerID).getCardID() == 2131165329) {
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|transferToPlayer|100|" + players.get(playerID).getUsername());
        }

        //chance18: Go back one space.
        if (players.get(playerID).getCardID() == 2131165330) {
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|-1|" + players.get(playerID).getUsername());
        }

        //chance19: Advance to Rathaus. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == 2131165331) {
            int incr = advanceAndCollect("Rathaus", playerID);

            players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|" + incr + "|" + players.get(playerID).getUsername());
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn|" + players.get(playerID).getUsername());
        }

        //community1: Bank error in your favor. Collect $200.
        if (players.get(playerID).getCardID() == 2131165342) {

        }


    }


}

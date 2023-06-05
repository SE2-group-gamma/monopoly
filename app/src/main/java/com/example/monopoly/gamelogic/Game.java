package com.example.monopoly.gamelogic;

import android.util.Log;

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

        //chance0, community0: Advance to "Go".
        if (players.get(playerID).getCardID() == 2131165320 || players.get(playerID).getCardID() == 2131165341) {
            int incr = fields.size() - players.get(playerID).getPosition();
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|" + incr + "|" + players.get(playerID).getUsername());
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn|" + players.get(playerID).getUsername());
        }

        //chance1: Advance to Strandbad. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == 2131165321) {
            advanceAndCollect(playerID, "Strandbad");
        }

        //chance2: Advance to Lindwurm. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == 2131165332) {
            advanceAndCollect(playerID, "Lindwurm");
        }

        //chance3, Your building loan matures. Receive $150.
        if (players.get(playerID).getCardID() == 2131165333) {
            transferToPlayerProtocol(playerID, 150);
            endTurnProtocol(playerID);
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

                transferPlayerToPlayerProtocol(playerID, owner.getId(), amount);
            }

            endTurnProtocol(playerID);
        }

        //chance5: Bank pays you dividend of $50.
        if (players.get(playerID).getCardID() == 2131165335) {
            transferToPlayerProtocol(playerID, 50);
            endTurnProtocol(playerID);
        }

        //chance6, chance12, community4: Get out of Jail Free.
        if (players.get(playerID).getCardID() == 2131165336 || players.get(playerID).getCardID() == 2131165324 || players.get(playerID).getCardID() == 2131165355) {
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
                    moveProtocol(playerID, incr);
                }
            }
            endTurnProtocol(playerID);
        }

        //chance10, You have been elected Chairman of the Board. Pay each player $50.
        if (players.get(playerID).getCardID() == 2131165322) {
            for (int i = 0; i < players.size(); i++) {
                if (i != playerID) {
                    transferPlayerToPlayerProtocol(playerID, i, 50);
                }
                endTurnProtocol(playerID);
            }
        }

        //chance11: Advance to City Arkaden. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == 2131165323) {
            advanceAndCollect(playerID, "City Arkaden");
        }

        //chance13, chance16, community5: Go to Jail directly.
        if (players.get(playerID).getCardID() == 2131165325 || players.get(playerID).getCardID() == 2131165328 || players.get(playerID).getCardID() == 2131165356) {
            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i).getName() == "Jail") {
                    int incr = fields.get(i).getId() - players.get(playerID).getPosition();
                    moveProtocol(playerID, incr);
                }
            }
        }

        //chance14, community14: Parking Ticket! Pay $15.
        if (players.get(playerID).getCardID() == 2131165326 || players.get(playerID).getCardID() == 2131165347) {
            transferToBankProtocol(playerID, 15);
        }

        //chance15: Advance two spaces.
        if (players.get(playerID).getCardID() == 2131165327) {
            moveProtocol(playerID, 2);
        }

        //chance17: Happy Birthday! Receive $100.
        if (players.get(playerID).getCardID() == 2131165329) {
            transferToPlayerProtocol(playerID, 100);
            endTurnProtocol(playerID);
        }

        //chance18: Go back one space.
        if (players.get(playerID).getCardID() == 2131165330) {
            moveProtocol(playerID, -1);
        }

        //chance19: Advance to Rathaus. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == 2131165331) {
            advanceAndCollect(playerID, "Rathaus");
        }

        //community1: Bank error in your favor. Collect $200.
        if (players.get(playerID).getCardID() == 2131165342) {
            transferToPlayerProtocol(playerID, 200);
            endTurnProtocol(playerID);
        }

        //community2: Doctor’s fee. Pay $50.
        if (players.get(playerID).getCardID() == 2131165353) {
            transferToBankProtocol(playerID, 50);
            endTurnProtocol(playerID);
        }

        //community3: From sale of stock you receive $50.
        if (players.get(playerID).getCardID() == 2131165354) {
            transferToPlayerProtocol(playerID, 50);
            endTurnProtocol(playerID);
        }

        //community6: Holiday fund matures. Receive $100.
        if (players.get(playerID).getCardID() == 2131165357) {
            transferToPlayerProtocol(playerID, 100);
            endTurnProtocol(playerID);
        }

        //community7: Income tax refund. Collect $20.
        if (players.get(playerID).getCardID() == 2131165358) {
            transferToPlayerProtocol(playerID, 20);
            endTurnProtocol(playerID);
        }

        //community8: It is your birthday. Collect $10 from every player.
        if (players.get(playerID).getCardID() == 2131165359) {
            for (int i = 0; i < players.size(); i++) {
                if (i != playerID) {
                    transferPlayerToPlayerProtocol(i, playerID, 10);
                }
            }
            endTurnProtocol(playerID);
        }

        //community9: Life insurance matures. Collect $100.
        if (players.get(playerID).getCardID() == 2131165360) {
            transferToPlayerProtocol(playerID, 100);
            endTurnProtocol(playerID);
        }

        //community10: Pay hospital fees of $100.
        if (players.get(playerID).getCardID() == 2131165343) {
            transferToBankProtocol(playerID, 100);
            endTurnProtocol(playerID);
        }

        //community11: Pay school fees of $50.
        if (players.get(playerID).getCardID() == 2131165344) {
            transferToBankProtocol(playerID, 50);
            endTurnProtocol(playerID);
        }

        //community12: Receive $25 consultancy fee.
        if (players.get(playerID).getCardID() == 2131165345) {
            transferToPlayerProtocol(playerID, 25);
            endTurnProtocol(playerID);
        }

        //community13: You are assessed for street repair. $40 per house. $115 per hotel.
        if (players.get(playerID).getCardID() == 2131165346) {
            //TODO: everything
        }

        //community15: You have won second prize in a beauty contest. Collect $10.
        if (players.get(playerID).getCardID() == 2131165348) {
            transferToPlayerProtocol(playerID, 10);
            endTurnProtocol(playerID);
        }

        //community16: You inherit $100.
        if (players.get(playerID).getCardID() == 2131165349) {
            transferToBankProtocol(playerID, 100);
            endTurnProtocol(playerID);
        }

        //community17: You receive $50 from warehouse sales.
        if (players.get(playerID).getCardID() == 2131165350) {
            transferToPlayerProtocol(playerID, 50);
            endTurnProtocol(playerID);
        }

        //community18: You receive a 7% dividend on preferred stock: $25.
        if (players.get(playerID).getCardID() == 2131165351) {
            transferToPlayerProtocol(playerID, 25);
            endTurnProtocol(playerID);
        }

        //community19: Receive $100 compensation for pain and suffering from the next player.
        if (players.get(playerID).getCardID() == 2131165352) {
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

    public void endTurnProtocol(int playerID) throws IOException {
        players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn|" + players.get(playerID).getUsername());
    }


    //TODO: change static IDs to R.drawable.chanceX
}

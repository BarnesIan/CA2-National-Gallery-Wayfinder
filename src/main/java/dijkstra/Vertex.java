package dijkstra;

public class Vertex {
    private String roomNum;
    private String name;
    private int xCoord,yCoord;

    public Vertex(String roomNum,String name,int xCoord,int yCoord){
        this.roomNum = roomNum;
        this.name = name;
        this.xCoord = xCoord;
        this.yCoord = yCoord;

    }

    public String getRoomNum() {
        return roomNum;
    }

    public String getName() {
        return name;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }
    @Override
    public String toString(){
        return name + roomNum + "\n";
    }
}

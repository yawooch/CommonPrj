package com.fakePocketmon;

public class Monster
{
    private String  monsterName     = "";      //몬스터이름
    private int     healthPoint     = 0;       //생명포인트
    private int     healthPointMax  = 100;     //생명포인트 풀피
    private int     attackPoint     = 0;       //공격력
    private String  elementAttr     = "";      //속성
    private boolean battleStatus    = true;    //전투가능상태 : true : 전투가능 , false : 전투불능
    

    public Monster()
    {
        this.healthPoint  = healthPointMax;
        this.attackPoint  = 50;
        this.elementAttr  = "무속성";//임시 속성 고정값
        this.monsterName  = "이브이";//임시 속성 고정값
        this.battleStatus = true;
    }
    
    public Monster(int healthPoint, int attackPoint, String elementAttr, boolean battleStatus, String monsterName)
    {
        this.healthPoint  = healthPoint;
        this.attackPoint  = attackPoint;
        this.elementAttr  = elementAttr;
        this.battleStatus = battleStatus;
        this.monsterName  = monsterName;
    }
    
    public boolean attack(Monster enemy, int damage)
    {
        System.out.println(enemy.getMonsterName() + "에게 " + damage + "만큼 데미지를 입혔다.");
        return enemy.attackedByEnemy(damage);
    }

    private boolean attackedByEnemy(int damage)
    {
        healthPoint = healthPoint - damage;
        
        if(healthPoint <= 0)
        {
            System.out.println(monsterName + "은(는) 전투불능이 되었다.");
            battleStatus = false;
        }
        
        return battleStatus;
    }
    public String printInfo()
    {
        String rtnInfo = "";
        
        rtnInfo += "\n";
        rtnInfo += "몬스터이름 : "  + monsterName + "\n";
        rtnInfo += "HP :"           + healthPoint + "\n";
        rtnInfo += "공격력 :"       + attackPoint + "\n";
        rtnInfo += "속성 :"         + elementAttr + "\n";
        rtnInfo += "전투가능상태 :" + (battleStatus?"전투가능":"전투불가") + "\n";
        return rtnInfo;
    }
    
    public void levelUp()
    {
         
    }
    

    public int getHealthPoint()
    {
        return healthPoint;
    }
    public void setHealthPoint(int healthPoint)
    {
        this.healthPoint = healthPoint;
    }
    public int getHealthPointMax()
    {
        return healthPointMax;
    }
    public void setHealthPointMax(int healthPointMax)
    {
        this.healthPointMax = healthPointMax;
    }
    public int getAttackPoint()
    {
        return attackPoint;
    }
    public void setAttackPoint(int attackPoint)
    {
        int diff =  attackPoint - this.attackPoint;
        System.out.println("공격력이 "+ diff + "으로 되었다.");
        this.attackPoint = attackPoint;
    }
    public String getElementAttr()
    {
        return elementAttr;
    }
    public void setElementAttr(String elementAttr)
    {
        this.elementAttr = elementAttr;
    }
    public boolean isBattleStatus()
    {
        return battleStatus;
    }
    public void setBattleStatus(boolean battleStatus)
    {
        this.battleStatus = battleStatus;
    }
    public String getMonsterName()
    {
        return monsterName;
    }
    public void setMonsterName(String monsterName)
    {
        this.monsterName = monsterName;
    }
}

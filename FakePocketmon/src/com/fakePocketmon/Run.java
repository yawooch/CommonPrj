package com.fakePocketmon;

public class Run
{
    public static void main(String[] args)
    {
        MonsterTrainer me = new MonsterTrainer();
        me.setTrainerLevel(12);
        FlameMonster mon = new FlameMonster();
        mon.setLevel(2);
        me.addMonster(mon);
        for(int i = 0; i < 20 ; i ++)
        {
            Monster enemy = PlayGroundController.randomMonster(me);
            System.out.println("Lv."+ enemy.getLevel() + " " + enemy.getMonsterName());
            me.setExperiencePoint(enemy);
            System.out.println(me.printInfo());;
            System.out.println("==========================================================");
        }
    }
}

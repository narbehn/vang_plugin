
package net.runelite.client.plugins.vanguards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import net.runelite.api.Actor;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import net.runelite.client.plugins.opponentinfo.OpponentInfoPlugin;
//import net.runelite.client.plugins.opponentinfo.OpponentInfoOverlay;

import javax.inject.Inject;

public class VanguardOverlay extends Overlay {

    private VanguardPlugin plugin;
    private final PanelComponent panelComponent = new PanelComponent();

    private static final int MAGE_VANGUARD_ID = 7525; //i think
    private static final int RANGE_VANGUARD_ID = 7528;
    private static final int MELEE_VANGUARD_ID = 7527;

    private int mageHp = 0;
    private int rangeHp = 0;
    private int meleeHp = 0;

    public String right_mage_str, right_range_str, right_melee_str = "";

    @Inject
    private Client client;


    @Inject
    public VanguardOverlay(VanguardPlugin plugin) {
        super(plugin);//?
        this.plugin = plugin;

        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        //this.opponentInfoPlugin = opponentInfoPlugin;

    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        Player p = client.getLocalPlayer(); //local player aka me
        Actor opponent = p.getInteracting(); //get entity i am interacting with
        //how to find its Id since it's an Actor not NPC specifically
        //if(opponent.getName().equals("Vanguard") && opponent.getHealth() > 0)//might wana double check the name
        //{
            if(opponent instanceof NPC)
            {
                int id = ((NPC) opponent).getId();
                if(id == MAGE_VANGUARD_ID)
                {
                    mageHp = opponent.getHealth();
                    right_mage_str = Integer.toString(mageHp);

                    System.out.println(mageHp);
                }
                else if (id == RANGE_VANGUARD_ID)
                {
                    rangeHp = opponent.getHealth();
                    right_range_str = Integer.toString(rangeHp);
                }
                else if (id == MELEE_VANGUARD_ID)
                {
                    meleeHp = opponent.getHealth();
                    right_melee_str = Integer.toString(meleeHp);
                }
            }
        //}


        //if (opponent == null) {
        //}


            panelComponent.getChildren().clear();
            String overlayTitle = "Vanguard HP";
            //title
            panelComponent.getChildren().add(TitleComponent.builder().text(overlayTitle).color(Color.RED).build());

            //size (width)
            panelComponent.setPreferredSize(new Dimension(graphics.getFontMetrics().stringWidth(overlayTitle) + 30, 0));

            panelComponent.getChildren().add(LineComponent.builder().left("Mage:").right(right_mage_str).build());

            panelComponent.getChildren().add(LineComponent.builder().left("Range:").right(right_range_str).build());

            panelComponent.getChildren().add(LineComponent.builder().left("Melee:").right(right_melee_str).build());

            return panelComponent.render(graphics);
    }
}
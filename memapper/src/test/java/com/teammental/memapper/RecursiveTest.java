package com.teammental.memapper;

import com.teammental.memapper.to.recursive.SourceFirstLevelTo;
import com.teammental.memapper.to.recursive.SourceSecondLevelTo;
import com.teammental.memapper.to.recursive.TargetFirstLevelTo;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Enclosed.class)
public class RecursiveTest {

  public static class WhenFieldIsSameType {

    @Test
    public void shouldMapSecondLevelType() {

      SourceSecondLevelTo sourceSecondLevelTo = new SourceSecondLevelTo();
      sourceSecondLevelTo.setTitle("title");
      sourceSecondLevelTo.setRank(5.5);

      SourceFirstLevelTo sourceFirstLevelTo = new SourceFirstLevelTo();
      sourceFirstLevelTo.setSourceSecondLevelTo(sourceSecondLevelTo);

      TargetFirstLevelTo targetFirstLevelTo = (TargetFirstLevelTo)
          MeMapper.from(sourceFirstLevelTo)
          .to(TargetFirstLevelTo.class);

      assertEquals(sourceSecondLevelTo.getTitle(),
          targetFirstLevelTo.getSourceSecondLevelTo().getTitle());

      assertEquals(sourceSecondLevelTo.getRank(),
          targetFirstLevelTo.getSourceSecondLevelTo().getRank());
    }

    @Test
    public void shouldMapSecondLevel_withNotReference() {

      SourceSecondLevelTo sourceSecondLevelTo = new SourceSecondLevelTo();
      sourceSecondLevelTo.setTitle("title");
      sourceSecondLevelTo.setRank(5.5);

      SourceFirstLevelTo sourceFirstLevelTo = new SourceFirstLevelTo();
      sourceFirstLevelTo.setSourceSecondLevelTo(sourceSecondLevelTo);

      TargetFirstLevelTo targetFirstLevelTo = (TargetFirstLevelTo)
          MeMapper.from(sourceFirstLevelTo)
              .to(TargetFirstLevelTo.class);

      sourceSecondLevelTo.setRank(7.7);
      sourceSecondLevelTo.setTitle("notitle");

      assertNotEquals(sourceSecondLevelTo.getTitle(),
          targetFirstLevelTo.getSourceSecondLevelTo().getTitle());

      assertNotEquals(sourceSecondLevelTo.getRank(),
          targetFirstLevelTo.getSourceSecondLevelTo().getRank());
    }
  }
}

import tester.*;

/*
    This interface defines a Volume type that can be converted to Cups, and can
    be added to other Volumes.
*/
interface Volume {
    /*
        This method returns the volume of the current object in US Cups.
    */
    public double toCups();

    /*
        This method returns a new Volume which should be the sum of the 
        current volume, and the volume of the parameter.
    */
    public Volume add(Volume v);
}

/*
    This abstract class provides a common add() method for all
    classes implementing Volume by using their toCups() method
    to convert all Volumes to the US Cup unit, and returing the
    sum of all their results.
*/
abstract class AVolume implements Volume {
    public Volume add (Volume v) {
        return new Cup(this.toCups() + v.toCups());
    }
}

/*
    This class represents Volumes measured in US cups.
*/
class Cup extends AVolume {
    double cups;

    Cup(double cups) {
        this.cups = cups;
    }

    public double toCups() {
        return this.cups;
    }
}

/*
    This class represents Volumes measured in US tablespoons.
*/
class Tablespoon extends AVolume {
    double tbsp;

    Tablespoon(double tbsp) {
        this.tbsp = tbsp;
    }

    public double toCups() {
        return this.tbsp / 16.0;
    }
}

/*
    A test class for checking our implementation of the various
    classes implementing Volume.
*/
class ExampleVolumes {
    // Test cups
    Volume oneCup = new Cup(1.0);
    Volume twoCups = new Cup(2.0);
    Volume threeCups = oneCup.add(twoCups);

    // Test tablespoons
    Volume oneTablespoon = new Tablespoon(1.0);
    Volume twoTablespoons = new Tablespoon(2.0);
    Volume eightTablespoons = new Tablespoon(8.0);
    Volume sixteenTablespoons = new Tablespoon(16.0);
    
    /*
        Notice that even though we are adding two objects of type 
        tablespoons, here our add method implementation only returns
        cups, so the result of calling add here returns a Cup object!
    */
    Volume threeTablespoons = oneTablespoon.add(twoTablespoons);
    Volume fourTablespoons = twoTablespoons.add(twoTablespoons);
    
    // Test adding cups and tablespoons
    Volume fourCups = threeCups.add(sixteenTablespoons);
    Volume fourAndAHalfCups = fourCups.add(eightTablespoons);
    Volume alsoFourCups = sixteenTablespoons.add(threeCups);
    Volume alsoFourAndAHalfCups = eightTablespoons.add(fourCups);

    boolean testCups(Tester t) {
        return t.checkExpect(1.0, oneCup.toCups()) &&
            t.checkExpect(2.0, twoCups.toCups()) &&
            t.checkExpect(3.0, threeCups.toCups());
    }

    boolean testTablespoons(Tester t) {
        return t.checkExpect(1.0 / 16.0, oneTablespoon.toCups()) &&
            t.checkExpect(2.0 / 16.0, twoTablespoons.toCups()) &&
            t.checkExpect(8.0 / 16.0, eightTablespoons.toCups()) &&
            t.checkExpect(16.0 / 16.0, sixteenTablespoons.toCups());
    }

    boolean testCoversions(Tester t) {
        return t.checkExpect(4.0, fourCups.toCups()) &&
            t.checkExpect(4.5, fourAndAHalfCups.toCups()) &&
            t.checkExpect(4.0, alsoFourCups.toCups()) &&
            t.checkExpect(4.5, alsoFourAndAHalfCups.toCups());
    }
}
package ny.gelato.extessera.data.model


/**
 * Created by jord.goldberg on 4/25/17.
 */

class JsonSpell {

    /**
     * casting_time : 1 action
     * classes : ["sorcerer","wizard"]
     * components : {"material":false,"raw":"V, S","somatic":true,"verbal":true}
     * description : You hurl a bubble of acid. Choose one creature within range, or choose two creatures within range that are within 5 feet of each other. A target must succeed on a Dexterity saving throw or take 1d6 acid damage.

     * This spell's damage increases by 1d6 when you reach 5th level (2d6), 11th level (3d6), and 17th level (4d6).
     * duration : Instantaneous
     * level : cantrip
     * job : Acid Splash
     * range : 60 feet
     * ritual : false
     * school : Conjuration
     * tags : ["sorcerer","wizard","cantrip"]
     * type : Conjuration cantrip
     * higher_levels : When you cast this spell using a spell slot of 2nd level or higher, you can affect one additional beast for each slot level above 1st.
     */

    var casting_time: String = ""
    var components: ComponentsBean = ComponentsBean()
    var description: String = ""
    var duration: String = ""
    var level: String = ""
    var name: String = ""
    var range: String = ""
    var ritual: Boolean = false
    var school: String = ""
    var type: String = ""
    var higher_levels: String = ""
    var classes: List<String> = ArrayList()
    var tags: List<String> = ArrayList()


    class ComponentsBean {
        /**
         * material : false
         * raw : V, S
         * somatic : true
         * verbal : true
         */

        var isMaterial: Boolean = false
        var raw: String = ""
        var isSomatic: Boolean = false
        var isVerbal: Boolean = false
    }
}

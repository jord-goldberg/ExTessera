package ny.gelato.extessera.data.model.character

import io.realm.RealmObject
import io.realm.annotations.Index

/**
 * Created by jord.goldberg on 6/20/17.
 */

open class Feature(
        @Index var name: String = ""

) : RealmObject()
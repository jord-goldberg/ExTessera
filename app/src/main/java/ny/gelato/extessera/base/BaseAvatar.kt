package ny.gelato.extessera.base

/**
 * Created by jord.goldberg on 6/8/17.
 *
 * Interface to be implemented by any ViewModel that interacts with a common.AvatarView
 */

interface BaseAvatar {
    val name: String
    val isInspired: Boolean
    val imagePath: String
    val imageUrl: String
}
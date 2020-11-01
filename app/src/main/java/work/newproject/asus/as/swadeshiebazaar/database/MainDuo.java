package work.newproject.asus.as.swadeshiebazaar.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDuo {
    @Insert(onConflict = REPLACE)
    long insertCartTable(CartTable table);

    @Insert(onConflict = REPLACE)
    long insertWishTable(wishListTable table);

    @Query("SELECT * FROM carttable")
    List<CartTable> getCartList();

    @Query("SELECT * FROM wishListTable")
    List<wishListTable> getWishList();

    @Delete
    void deleteItem(CartTable table);

    @Query("DELETE FROM carttable")
    void deleteList();

    @Delete
    void deleteWishList(wishListTable table);

    @Query("DELETE FROM carttable WHERE productID = :productID")
    void deleteByProductID(long productID);

    @Query("DELETE FROM wishListTable WHERE productID = :productID")
    void deleteByProductIDWishList(long productID);

    @Query("UPDATE carttable SET  qty = :sQty , price = :sPrice WHERE productID = :sID")
    void update(long sID,String sQty,String sPrice);

    @Query("UPDATE wishListTable SET  qty = :sQty , price = :sPrice WHERE productID = :sID")
    void updateWishList(long sID,String sQty,String sPrice);
}

package org.d3if0069.miniprojek2.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if0069.miniprojek2.model.Taekwondo

@Dao
interface TaekwondoDao {

    @Insert
    suspend fun insert(taekwondo: Taekwondo)

    @Update
    suspend fun update(taekwondo: Taekwondo)

    @Query("SELECT * FROM taekwondo ORDER BY nama DESC")
    fun getTaekwondo(): Flow<List<Taekwondo>>
    @Query("SELECT * FROM taekwondo WHERE id = :id")
    suspend fun getTaekwondoById(id: Long): Taekwondo?

    @Query("DELETE FROM taekwondo WHERE id = :id")
    suspend fun deletedById(id: Long)

}
package usecase

import com.example.core.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import usecase.base.CoroutinesDispatchers
import usecase.base.ResultStatus
import usecase.base.UseCase
import javax.inject.Inject

interface CheckFavoriteUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Boolean>>

    data class Params(val characterId: Int)
}

class CheckFavoriteUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<CheckFavoriteUseCase.Params, Boolean>(), CheckFavoriteUseCase {

    override suspend fun doWork(params: CheckFavoriteUseCase.Params): ResultStatus<Boolean> {
        return withContext(dispatchers.io()) {
            val isFavorite = favoritesRepository.isFavorite(params.characterId)
            ResultStatus.Success(isFavorite)
        }
    }
}
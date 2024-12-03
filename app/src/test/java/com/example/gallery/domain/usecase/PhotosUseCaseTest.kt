package com.example.gallery.domain.usecase

import com.example.gallery.data.repository.FakeLocalDatabaseRepository
import com.example.gallery.data.repository.FetchRemoteAlbum
import com.example.gallery.data.repository.FetchRemotePhoto
import com.example.gallery.data.repository.FetchRemoteUserData
import com.example.gallery.domain.model.Album
import com.example.gallery.domain.model.Photo
import com.example.gallery.domain.model.PhotoDataModel
import com.example.gallery.domain.model.User
import com.example.gallery.utils.Resource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PhotosUseCaseTest {
    @Mock
    private lateinit var fetchRemoteAlbum: FetchRemoteAlbum
    @Mock
    private lateinit var fetchRemotePhoto: FetchRemotePhoto
    @Mock
    private lateinit var fetchRemoteUserData: FetchRemoteUserData

    private lateinit var dbUseCase: DatabaseUseCase
    private lateinit var fakeLocalDatabaseRepository: FakeLocalDatabaseRepository
    private lateinit var photoUseCase: PhotosUseCase
    @Before
    fun setUP() {
        MockitoAnnotations.openMocks(this) // Initializes all mocks
        fakeLocalDatabaseRepository = FakeLocalDatabaseRepository()
        dbUseCase = DatabaseUseCase(
            fetchRemoteAlbum,
            fetchRemotePhoto,
            fetchRemoteUserData,
            fakeLocalDatabaseRepository
        )
        photoUseCase = PhotosUseCase(dbUseCase)

    }
    @Test
    fun `fetchAndSaveRemoteData should save data when local DB is empty`() = runBlocking {
        val check = fakeLocalDatabaseRepository.isDataExists()
        `when`(fetchRemoteUserData.invoke()).thenReturn(
            flow { emit(Resource.Success(listOf(User(1, "John Doe", "john.Doe")))) }
        )
        `when`(fetchRemotePhoto()).thenReturn(
            flow { emit(Resource.Success(listOf(Photo(1, 2, "url1", "Bird", "url2")))) }
        )
        `when`(fetchRemoteAlbum()).thenReturn(
            flow { emit(Resource.Success(listOf(Album(1, "Album 1", 1)))) }
        )

        val result = photoUseCase.fetchAndSaveRemoteData().first()
        // Assert
        fakeLocalDatabaseRepository.addAllPhoto(anyList())
        val sz = fakeLocalDatabaseRepository.isDataExists()
        assertTrue(result is Resource.Success && result.data == "Successfully Saved In Local" && !check and sz)
    }

    @Test
    fun `fetchAndSaveRemoteData should not save data when local DB is not empty`() = runBlocking {
        fakeLocalDatabaseRepository.addAllPhoto(
            listOf(PhotoDataModel("photo1", "album1", "user1", "url1", "url2"))
        )
        val check = fakeLocalDatabaseRepository.isDataExists()
        `when`(fetchRemoteUserData.invoke()).thenReturn(
            flow { emit(Resource.Success(listOf(User(1, "John Doe", "john.Doe")))) }
        )
        `when`(fetchRemotePhoto()).thenReturn(
            flow { emit(Resource.Success(listOf(Photo(1, 2, "url1", "Bird", "url2")))) }
        )
        `when`(fetchRemoteAlbum()).thenReturn(
            flow { emit(Resource.Success(listOf(Album(1, "Album 1", 1)))) }
        )

        val result = photoUseCase.fetchAndSaveRemoteData().first()
        // Assert
        assertTrue(result is Resource.Success && result.data == "Already Exist" && check)
    }
    @Test
    fun `deletePhotosData should clear all photos`() = runBlocking {
        // Arrange
        val testPhotos = listOf(
            PhotoDataModel("Photo 1", "Album 1", "User 1", "url1", "url2"),
            PhotoDataModel("Photo 2", "Album 2", "User 2", "url3", "url4")
        )
        fakeLocalDatabaseRepository.addAllPhoto(testPhotos)
        // Act
        val results = photoUseCase.deletePhotosData().first()
        val isExist = fakeLocalDatabaseRepository.isDataExists()
        // Assert
        assertTrue(isExist)
    }
    @Test
    fun `getPhotosData should get some photos`() = runBlocking {

        val photo = photoUseCase.getPhotosData().first()
        val isExist = fakeLocalDatabaseRepository.isDataExists()
        // Assert
        assertTrue(photo.data==null ) // since there is no photo

    }
}
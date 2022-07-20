package com.sistemasmcanicosjh.data
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import com.sistemasmcanicosjh.model.Clientes

class ClientesDao {
    private var codigoUsuario: String
    private var firestore: FirebaseFirestore

    init {
        val usuario = Firebase.auth.currentUser?.email
        codigoUsuario = "$usuario"
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun obtenerClientes() : MutableLiveData<List<Clientes>>{
        val listaClientes = MutableLiveData<List<Clientes>>()
        firestore.collection("SistemasMecanicosApp")
            .document(codigoUsuario)
            .collection("Clientes")
            .addSnapshotListener{snapshot, e ->
                if(e != null){
                    return@addSnapshotListener
                }
                if(snapshot != null){
                    val lista = ArrayList<Clientes>()
                    val clientes = snapshot.documents
                    clientes.forEach{
                        val cliente = it.toObject(Clientes::class.java)
                        if(cliente!=null){
                            lista.add(cliente)
                        }
                    }
                    listaClientes.value = lista
                }
            }
        return listaClientes
    }

    fun saveCliente(clientes: Clientes){
        val document: DocumentReference
        if(clientes.id.isEmpty()){
            document = firestore
                .collection("SistemasMecanicosApp")
                .document(codigoUsuario)
                .collection("Clientes")
                .document()
            clientes.id = document.id
        }else{
            document = firestore
                .collection("SistemasMecanicosApp")
                .document(codigoUsuario)
                .collection("Clientes")
                .document(clientes.id)
        }
        val set = document.set(clientes)
        set.addOnSuccessListener {
            Log.d("AddCliente","Cliente agregado")
        }
            .addOnCanceledListener {
                Log.e("AddCliente","Cliente No agreado")
            }
    }

    fun deleteCliente(clientes:Clientes){
        if(clientes.id.isNotEmpty()){
            firestore
                .collection("SistemasMecanicosApp")
                .document(codigoUsuario)
                .collection("Clientes")
                .document(clientes.id)
                .delete()
                .addOnSuccessListener {
                    Log.d("deleteCliente","Cliente Eliminado")
                }
                .addOnCanceledListener {
                    Log.e("deleteLugar","Cliente No Eliminado")
                }
        }
    }
}
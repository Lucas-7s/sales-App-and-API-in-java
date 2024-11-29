package com.example.aplicativopim.model;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Body;

public interface PedidoService {
    @GET("api/pedidos")
    Call<List<Pedido>> getPedidos();

    @GET("api/pedidos/{id}")
    Call<Pedido> getPedido(@Path("id") Long id);

    // Métodos adicionais que você pode precisar
    @POST("api/pedidos")
    Call<Pedido> createPedido(@Body Pedido pedido);

    @PUT("api/pedidos/{id}")
    Call<Pedido> updatePedido(@Path("id") Long id, @Body Pedido pedido);

    @DELETE("api/pedidos/{id}")
    Call<Void> deletePedido(@Path("id") Long id);
}
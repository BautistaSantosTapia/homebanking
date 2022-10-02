
const app = Vue.
createApp({
    data(){
        return{
            cliente1:[],
            cards1:[],
            creditCards:[],
            debitCards:[],
            cartaABorrar:{},
            arrayCartasTrue:[],
            arrayCreditoTrue:[],
            arrayDebitoTrue:[],
            //fecha:[],
        }
    },
    created(){
        this.cardsGet()
    },
    methods:{
        cardsGet(){
            axios.get('/api/clients/current')
            .then((response) => {
                console.log(response);
                this.cliente1 = response.data
                console.log(this.cliente1);
                this.cards1 = this.cliente1.cards
                console.log(this.cards1);
                /*console.log(this.cards1.type);*/
                console.log("hola");
                this.filtroCredito()
                this.filtroDebito()
                this.filtroTrue()
                this.filtroCreditoTrue()
                this.filtroDebitoTrue()

                this.fechaAhora()

            })
            .catch((error)=>console.log(error))
        },
        filtroTrue(){
            this.arrayCartasTrue = this.cards1.filter(card => card.activeCard == true)
            console.log(this.arrayCartasTrue);
        },
        filtroCredito(){
            this.creditCards = this.cards1.filter(card => card.type == "credito")
            console.log(this.creditCards);
        },
        filtroCreditoTrue(){
            this.arrayCreditoTrue = this.creditCards.filter(card => card.activeCard == true)
            console.log(this.arrayCreditoTrue);
        },
        filtroDebito(){
            this.debitCards = this.cards1.filter(card => card.type == "debito")
            console.log(this.debitCards);
        },
        filtroDebitoTrue(){
            this.arrayDebitoTrue = this.debitCards.filter(card => card.activeCard == true)
            console.log(this.arrayDebitoTrue);
        },
        fechaLinda(date){
            fecha = new Date(date).toLocaleDateString();
            return fecha;
        },
        fechaAhora(){
            ahora = new Date();
            ahora = ahora.toLocaleDateString();
            return ahora;
        },
        postCerrar(){
          axios.post('/api/logout').then(response => console.log('signed out!!!'))
        },
        postDelete(id){
            console.log(id);
            this.cartaABorrar = this.cards1.filter(carta => carta.id == id)
            console.log(this.cartaABorrar);
            console.log(this.cartaABorrar[0]);
            console.log(this.cartaABorrar[0].activeCard);
            this.cartaABorrar[0].activeCard = false;
            console.log(this.cartaABorrar[0].activeCard);
            console.log(this.cartaABorrar[0].id);
            axios.patch(`/api/clients/current/cards/${this.cartaABorrar[0].id}`)
            .then((response)=>{
                console.log("patcheado");
                window.location.href="/Web/cards.html"}
            )
            .catch(function (error) {
                if (error.response) {
                    console.log(error.response.data);
                    swal({
                        title: "error",
                        text: error.response.data, 
                        icon: "error",
                    })
                }}
            );
        },
        confirmacionDelete(id){

                swal({
                    title: "Estas seguro/a de borrar esta tarjeta?",
                    text: "Recorda que una vez que aceptes no habra marcha atras",
                    icon: "warning",
                    buttons: true,
                    dangerMode: true,
                  })
                    .then((willDelete) => {
                        if (willDelete) {
                            swal("La tarjeta se ha borrado correctamente!", {icon: "success",});
    
                            console.log(id);
                            this.cartaABorrar = this.cards1.filter(carta => carta.id == id)
                            console.log(this.cartaABorrar);
                            console.log(this.cartaABorrar[0]);
                            console.log(this.cartaABorrar[0].activeCard);
                            this.cartaABorrar[0].activeCard = false;
                            console.log(this.cartaABorrar[0].activeCard);
                            console.log(this.cartaABorrar[0].id);
                            axios.patch(`/api/clients/current/cards/${this.cartaABorrar[0].id}`)
                            .then((response)=>{
                                console.log("patcheado");
                                window.location.href="/Web/cards.html"}
                            )
                            .catch(function (error) {
                                if (error.response) {
                                    console.log(error.response.data);
                                    swal({
                                        title: "error",
                                        text: error.response.data, 
                                        icon: "error",
                                    })
                                }}
                            );

                        } else {
                            swal("No has borrado la tarjeta", {icon: "error",});
                        }
                    });
            
        
        },
    },
    computed:{
        
    },
}).mount('#app')
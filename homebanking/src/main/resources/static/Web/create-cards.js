const app = Vue.
createApp({
    data(){
        return{
            type:"",
            color:"",
        }
    },
    created(){
        console.log("cccccccccccc");
    },
    methods:{
        postCerrar(){
            axios.post('/api/logout').then(response => console.log('signed out!!!'))
        },
        postCard(){
            axios.post('/api/clients/current/cards',`type=${this.type}&color=${this.color}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response =>{
                    console.log('tarjeta creada');
                    window.location.href="/Web/cards.html"})
                .catch(function (error) {
                    if (error.response) {
                      // La respuesta fue hecha y el servidor respondió con un código de estado
                      // que esta fuera del rango de 2xx
                      console.log(error.response.data);
                    swal({
                        title: "error",
                        text: error.response.data, 
                        icon: "error",
                    })
                      /*console.log(error.response.status);
                      console.log(error.response.headers);*/
                    } else if (error.request) {
                      // La petición fue hecha pero no se recibió respuesta
                      // `error.request` es una instancia de XMLHttpRequest en el navegador y una instancia de
                      // http.ClientRequest en node.js
                      console.log(error.request);
                    } else {
                      // Algo paso al preparar la petición que lanzo un Error
                      console.log('Error', error.message);
                    }
                    console.log(error.config);
                });
        },
       
    },
    computed:{
        
    },
}).mount('#app')
db = connect( 'mongodb://localhost:27017/proyectotest?authSource=admin' );

db.client.insertMany([
    {
        _id: 'Cliente1',
        nickname: 'juanito',
        city: 'Armenia',
        profilePhoto: 'mi foto',
        email: 'juan@email.com',
        password: 'mipassword',
        name: 'Juan',
        state: 'ACTIVE',
        _class: 'co.edu.uniquindio.proyecto.model.documents.Client'
    },
    {
        _id: 'Cliente2',
        nickname: 'maria',
        city: 'Armenia',
        profilePhoto: 'mi foto',
        email: 'maria@email.com',
        password: 'mipassword',
        name: 'Maria',
        state: 'ACTIVE',
        _class: 'co.edu.uniquindio.proyecto.model.documents.Client'    },
    {
        _id: 'Cliente3',
        nickname: 'pedrito',
        city: 'Armenia',
        profilePhoto: 'mi foto',
        email: 'pedro@email.com',
        password: 'mipassword',
        name: 'Pedro',
        state: 'ACTIVE',
        _class: 'co.edu.uniquindio.proyecto.model.documents.Client'
    }

]);

db.business.insertMany([
    {
        _id: 'Negocio1',
        name: 'Restaurante Mexicano',
        description: 'Restaurante de comida mexicana en Armenia',
        idClient: 'Cliente1',
        location: {
            latitude: 4.540130,
            longitude: -75.665660
        },
        images: ['imagen1', 'imagen2'],
        typeBusiness: 'RESTAURANTE',
        timeSchedules: [
            {
                day: 'LUNES',
                start: '08:00',
                end: '20:00'
            }
        ],
        phone: ['1234567', '7654321'],
        state: 'ACTIVE',
        _class: 'co.edu.uniquindio.proyecto.model.documents.Business'
    }
]);

db.comment.insertMany([
    {
        message: "Excelente sitio, muy buena atención",
        date: new Date(),
        idClient: 'Cliente1',
        idBusiness: 'Negocio1',
        rating: 5,
        _class: 'co.edu.uniquindio.proyecto.model.documents.Comment'
    }

]);
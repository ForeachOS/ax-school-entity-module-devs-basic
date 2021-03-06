package com.foreach.across.samples.musical.application.domain.musical;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/musicals")
@RequiredArgsConstructor
public class MusicalApiController
{
	private final MusicalRepository musicalRepository;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<MusicalDto>> getMusicals() {
		return ResponseEntity.ok(
				musicalRepository.findAll().stream()
				                 .map( MusicalDto::from )
				                 .collect( Collectors.toList() )
		);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<MusicalDto> getMusicalById( @PathVariable UUID id ) {
		Musical musical = musicalRepository.findOne( id );

		if ( musical == null ) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok( MusicalDto.from( musical ) );
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<MusicalDto> createMusical( @RequestBody MusicalDto musicalDto ) {
		Musical musical = musicalDto.toMusical();

		musical = musicalRepository.save( musical );

		return ResponseEntity.ok( MusicalDto.from( musical ) );
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public ResponseEntity<MusicalDto> updateMusical( @PathVariable("id") Musical existingMusical, @RequestBody MusicalDto musicalDto ) {
		existingMusical.setName( musicalDto.getName() );
		existingMusical.setDescription( musicalDto.getDescription() );

		existingMusical = musicalRepository.save( existingMusical );

		return ResponseEntity.ok( MusicalDto.from( existingMusical ) );
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity deleteMusicalById( @PathVariable UUID id ) {
		musicalRepository.delete( id );
		return ResponseEntity.ok().build();
	}
}
